package com.test.processor.simple

import com.rabbitmq.client.QueueingConsumer
import com.test.util.ConnectionUtil


/**
 * 消费者从队列中获取消息
 *
 * @author 费世程
 * @date 2019/10/22 14:47
 */
class Receiver {
}

fun main() {

  val QUEUE_NAME = "rabbit_mq_test_1"

  //获取到连接以及MQ通道
  val connection = ConnectionUtil.getConnection()
  //从连接中创建通道
  val channel = connection.createChannel()
  //声明队列
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  //定义队列的消费者
  val consumer= QueueingConsumer(channel)

  /*
    监听队列：
    String queue, 队列名称
    boolean autoAck, 自动回复，当消费者接收到消息后要告诉mq消息已接收
    Consumer callback 消费方法，当消费者接收到消息要执行的方法
   */
  channel.basicConsume(QUEUE_NAME, true, consumer)
  //获取消息
  while (true){
    val delivery=consumer.nextDelivery()
    val message=String(delivery.body)
    println("[X] received '$message'")
  }

}