package com.test.processor.subscribe.direct

import com.rabbitmq.client.QueueingConsumer
import com.test.processor.subscribe.fanout.Subscribe_receiver_a
import com.test.util.ConnectionUtil

/**
 * @author 费世程
 * @date 2019/10/24 14:23
 */
class Receiver_B {

  companion object{
    private val QUEUE_NAME="test_queue_direct_2"
    private val EXCHANGE_NAME="test_exchange_direct"

    fun receive(){
      val connection=ConnectionUtil.getConnection()
      val channel=connection.createChannel()

      channel.queueDeclare(QUEUE_NAME,false,false,false,null)

      //绑定队列到交换机
      channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"insert")

      channel.basicQos(1)

      val consumer=QueueingConsumer(channel)
      //监听队列，手动返回完成
      channel.basicConsume(QUEUE_NAME,false,consumer)

      //获取消息
      while(true){
        val delivery=consumer.nextDelivery()
        val message=String(delivery.body)
        println("[receiver-b] : $message")
        Thread.sleep(10)
        channel.basicAck(delivery.envelope.deliveryTag,false)
      }
    }
  }

}