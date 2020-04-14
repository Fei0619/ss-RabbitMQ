package com.test.processor.work;

import com.rabbitmq.client.*;
import com.test.util.ConnectionUtil;

import java.io.IOException;

/**
 * @author 费世程
 * @date 2020/4/7 16:02
 */

public class ReceiverA {

  private static final String QUEUQ_NAME = "work_queue_test";

  public static void main(String[] args) {
    try {
      //获取连接
      Connection connection = ConnectionUtil.Companion.getConnection();
      //创建会话通道
      final Channel channel = connection.createChannel();
      channel.basicQos(1);
      //声明队列
      channel.queueDeclare(QUEUQ_NAME, false, false, false, null);
      //实现消费方法
      DefaultConsumer consumer = new DefaultConsumer(channel) {
        //获取消息，并处理。这个方法类似事件监听，如果有消息的时候会被自动调用
        @Override
        public void handleDelivery(String consumerTag,
                                   Envelope envelope,
                                   AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
          String msg = new String(body, "UTF-8");
          System.out.println("消费者1 received :" + msg);
          /*
            手动进行ACk
            long deliveryTag, 用来标识消息的id
            boolean multiple 是否批量
           */
          channel.basicAck(envelope.getDeliveryTag(),false);
        }
      };
      //监听队列,第二个参数false，手动进行ACK
      channel.basicConsume(QUEUQ_NAME, false, consumer);
    } catch (Exception e) {
      System.err.println("消费者1--接收消息出现异常：" + e.getMessage());
    }
  }

}
