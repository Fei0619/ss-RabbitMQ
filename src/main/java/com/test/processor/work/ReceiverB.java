package com.test.processor.work;

import com.rabbitmq.client.*;
import com.test.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 费世程
 * @date 2020/4/7 16:18
 */
public class ReceiverB {

  private static final String QUEUQ_NAME = "work_queue_test";

  public static void main(String[] args) {
    try {
      Connection connection = ConnectionUtil.Companion.getConnection();
      final Channel channel = connection.createChannel();
      //设置每个消费者同时只能处理一条消息（能者多劳），在手动ack下才生效
      channel.basicQos(1);
      channel.queueDeclare(QUEUQ_NAME,false,false,false,null);
      DefaultConsumer consumer=new DefaultConsumer(channel){
        @Override
        public void handleDelivery(String consumerTag,
                                   Envelope envelope,
                                   AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
          String msg = new String(body, "UTF-8");
          System.out.println("消费者2 received :" + msg);
          channel.basicAck(envelope.getDeliveryTag(),false);
          try {
            TimeUnit.SECONDS.sleep(2L);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      };
      channel.basicConsume(QUEUQ_NAME,false,consumer);

    } catch (Exception e) {
      System.err.println("消费者2--接收消息出现异常：" + e.getMessage());
    }

  }
}
