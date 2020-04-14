package com.test.processor.subscribe.fanout

import com.rabbitmq.client.QueueingConsumer
import com.test.util.ConnectionUtil

/**
 * 消费者-a（看作是前台系统）
 *
 * @author 费世程
 * @date 2019/10/23 17:57
 */
class Subscribe_receiver_a {

  companion object{

    private val QUEUE_NAME="test_queue_subscribe_1"
    private val EXCHANGE_NAME="test_exchange_fanout"

    fun receive(){
      //获取到连接以及MQ通道
      val connection=ConnectionUtil.getConnection()
      val channel=connection.createChannel()
      //声明队列
      channel.queueDeclare(QUEUE_NAME,false,false,false,null)
      //绑定队列到交换机
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"")
      //同一时刻服务器只会发一条消息给消费者
      channel.basicQos(1)

      //定义队列的消费者
      val consumer=QueueingConsumer(channel)
      //监听队列，手动返回完成
      channel.basicConsume(QUEUE_NAME,false,consumer)

      //获取消息
      while(true){
        val delivery=consumer.nextDelivery()
        val message=String(delivery.body)
        println("[receiver-a] : $message")
        Thread.sleep(10)
        channel.basicAck(delivery.envelope.deliveryTag,false)
      }
    }
  }

}