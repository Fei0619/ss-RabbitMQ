package com.test.processor.subscribe.direct

import com.test.util.ConnectionUtil
import java.nio.charset.StandardCharsets

/**
 * @author 费世程
 * @date 2019/10/24 14:17
 */
class Sender {
}

fun main() {
  val EXCHANGE_NAME="test_exchange_direct"

  val connection=ConnectionUtil.getConnection()
  val channel=connection.createChannel()

  //声明exchange
  //direct类型：它会把消息路由到那些binding key与routing key完全匹配的Queue中
  channel.exchangeDeclare(EXCHANGE_NAME,"direct")

  //消息内容
  val message="修改信息"
  //delete 是消息的key
  //参数：String exchange, String routingKey, BasicProperties props, byte[] body
  channel.basicPublish(EXCHANGE_NAME,"update",null,message.toByteArray(StandardCharsets.UTF_8))
  println("[x] sent '$message'")

  channel.close()
  connection.close()

}