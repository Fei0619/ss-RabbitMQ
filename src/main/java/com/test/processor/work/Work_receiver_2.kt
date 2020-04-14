package com.test.processor.work

import com.rabbitmq.client.QueueingConsumer
import com.test.util.ConnectionUtil

/**
 * @author 费世程
 * @date 2019/10/23 17:20
 */
class Work_receiver_2 {
  companion object{
    private val QUEUE_NAME="test_queue_work"

    fun receive(){
      //获取到连接以及MQ通道
      val connection= ConnectionUtil.getConnection()
      val channel=connection.createChannel()
      //声明队列
      channel.queueDeclare(QUEUE_NAME,false,false,false,null)
      //同一时刻服务器只会发一条消息给消费者
      channel.basicQos(1)

      //定义队列的消费者
      val consumer= QueueingConsumer(channel)
      //监听队列，false=手动返回完成状态，true=自动
      channel.basicConsume(QUEUE_NAME,false,consumer)

      //获取消息
      while (true){
        val delivery=consumer.nextDelivery()
        val message=String(delivery.body)
        println("[b] received '$message'")
        //休眠
        Thread.sleep(1000)
        //返回确认状态，注释掉表示使用自动确认模式
        channel.basicAck(delivery.envelope.deliveryTag,false)
      }
    }
  }
}