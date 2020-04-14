package com.test.processor.subscribe.fanout;

import com.rabbitmq.client.*;
import com.test.util.ConnectionUtil;

import java.io.IOException;

/**
 * @author 费世程
 * @date 2020/4/7 17:33
 */
public class ReceiverA {

  private final static String QUEUE_NAME = "test_queue_subscribe_1";
  private final static String EXCHANGE_NAME = "test_exchange_fanout";

  public static void main(String[] args) {

    try {
      Connection connection = ConnectionUtil.Companion.getConnection();
      //获取通道
      Channel channel = connection.createChannel();
      //声明队列
      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
      /*
        绑定队列到交换机
        String queue, String exchange, String routingKey
       */
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
      DefaultConsumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag,
                                   Envelope envelope,
                                   AMQP.BasicProperties properties,
                                   byte[] body) throws IOException {
          String msg = new String(body, "UTF-8");
          System.out.println("receiver A :" + msg);
        }
      };
      channel.basicConsume(QUEUE_NAME, false, consumer);
    } catch (Exception e) {
      System.err.println("receiverA 接收消息出现异常：" + e);
    }

  }

}
