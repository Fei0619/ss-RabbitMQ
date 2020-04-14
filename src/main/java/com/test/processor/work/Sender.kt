package com.test.processor.work

import com.test.util.ConnectionUtil
import java.nio.charset.StandardCharsets

/**
 * work模式：
 * 一个生产者+两个消费者，但是一个消息只能被一个消费者获取
 *
 * @author 费世程
 * @date 2019/10/23 17:21
 */
class Sender {
}

fun main() {
  val QUEUE_NAME="work_queue_test"

  //获取到连接以及MQ通道
  val connection=ConnectionUtil.getConnection()
  val channel=connection.createChannel()
  //声明队列
  channel.queueDeclare(QUEUE_NAME,false,false,false,null)

  //向队列中发送60条消息
  for (i in 1..60){
    val message="hello world $i"
    channel.basicPublish("",QUEUE_NAME,null,message.toByteArray(StandardCharsets.UTF_8))
    println("[x] send $message")
  }
  channel.close()
  connection.close()
}