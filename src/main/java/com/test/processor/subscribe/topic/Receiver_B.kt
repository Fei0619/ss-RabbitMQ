package com.test.processor.subscribe.topic

import com.rabbitmq.client.QueueingConsumer
import com.test.util.ConnectionUtil

/**
 * @author 费世程
 * @date 2019/10/24 15:01
 */
class Receiver_B {

  companion object{
    private val QUEUE_NAME="test_queue_topic_2"
    private val EXCHANGE_NAME="test_exchange_topic"

    fun receive(){
      val connection=ConnectionUtil.getConnection()
      val channel=connection.createChannel()

      //声明队列
      //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
      channel.queueDeclare(QUEUE_NAME,false,false,false,null)

      //绑定队列到交换机
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"*.routeKey.#")

      channel.basicQos(1)

      val consumer=QueueingConsumer(channel)
      //监听队列，手动返回完成
      //String queue, boolean autoAck, Consumer callback
      channel.basicConsume(QUEUE_NAME,false,consumer)

      while (true){
        val delivery=consumer.nextDelivery()
        val message=String(delivery.body)
        println("receiver_b - $message")
        Thread.sleep(10)
        channel.basicAck(delivery.envelope.deliveryTag,false)
      }
    }

  }

}