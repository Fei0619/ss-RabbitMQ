package com.test.processor.subscribe.topic

import com.test.util.ConnectionUtil
import java.nio.charset.StandardCharsets

/**
 * @author 费世程
 * @date 2019/10/24 14:52
 */
class Sender {
}

fun main() {
  val EXCHANGE_NAME="test_exchange_topic"

  val connection=ConnectionUtil.getConnection()
  val channel=connection.createChannel()

  channel.exchangeDeclare(EXCHANGE_NAME,"topic")

  val message="Hello World !!!"
  /*
    Routingkey一般都是由一个或者多个单词组成，多个单词之间以“.”分隔
    通配符规则：
      #：匹配一个或多个词
      *：匹配不多不少恰好一个词
   */
  channel.basicPublish(EXCHANGE_NAME,"routeKey.1",null,message.toByteArray(StandardCharsets.UTF_8))
  println("[x] send '$message'")

  channel.close()
  connection.close()

}