package com.test.processor.subscribe.fanout

import com.test.util.ConnectionUtil
import java.nio.charset.StandardCharsets

/**
 * 订阅模式：
 * （1）一个生产者，多个消费者
 * （2）每一个消费者都有自己的一个队列
 * （3）生产者没有将消息直接发送到队列，而是发送到了交换机
 * （4）每个队列都要绑定到交换机
 * （5）生产者发送的消息经过交换机到达队列，实现，一个消息被多个消费者获取的目的
 * 注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
 *
 * @author 费世程
 * @date 2019/10/23 17:44
 */
class Sender {
}

/**
 * 消息的生产者（看作是后台系统）
 *
 * 消息发送到没有队列绑定的交换机时，消息将丢失
 * 因为，交换机没有存储消息的能力，消息只能存储在队列中
 */
fun main() {

  val EXCHANGE_NAME="test_exchange_fanout"

  val connection=ConnectionUtil.getConnection()
  val channel=connection.createChannel()

  //声明exchange
  //fanout类型的exchange：它会把所有发送到该exchange的消息路由到所有与他绑定的queue中
  //exchangeDeclare(String exchange, String type)
  channel.exchangeDeclare(EXCHANGE_NAME,"fanout")

  val message="Hello World!"
  //String exchange, String routingKey, BasicProperties props, byte[] body
  channel.basicPublish(EXCHANGE_NAME,"",null,message.toByteArray(StandardCharsets.UTF_8))
  println("[x] send $message")

  channel.close()
  connection.close()

}