package com.test.processor.subscribe.fanout

import com.rabbitmq.client.QueueingConsumer
import com.test.util.ConnectionUtil

/**
 * 消费者-b（看作是搜索系统）
 * @author 费世程
 * @date 2019/10/24 9:25
 */
class Subscribe_receiver_b {

  companion object{

    private val QUEUE_NAME="test_queue_subscribe_2"
    private val EXCHANGE_NAME="test_exchange_fanout"

    fun receive(){
      //获取到连接以及MQ通道
      val connection=ConnectionUtil.getConnection()
      val channel=connection.createChannel()
      //声明队列
      channel.queueDeclare(QUEUE_NAME,false,false,false,null)
      //绑定队列到交换机
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"")
      //统一时刻服务器只能发送一条消息给消费者
      channel.basicQos(1)

      //定义队列的消费者
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