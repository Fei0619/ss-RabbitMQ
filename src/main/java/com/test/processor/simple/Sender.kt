package com.test.processor.simple

import com.test.util.ConnectionUtil
import java.nio.charset.StandardCharsets

/**
 * 生产者发送消息到队列
 *
 * @author 费世程
 * @date 2019/10/22 14:04
 */
class Send{

}

fun main() {

  val QUEUE_NAME="rabbit_mq_test_1"

  //获取到连接以及MQ通道
  val connection= ConnectionUtil.getConnection()
  //从连接中创建通道
  val channel=connection.createChannel()
  /*
    声明（创建）队列：
    String queue, 队列名称
    boolean durable, 是否持久化，如果持久化，mq重启后队列还在
    boolean exclusive, 是否独占连接
    boolean autoDelete, 自动删除
    Map<String, Object> arguments 参数
   */
  channel.queueDeclare(QUEUE_NAME,false,false,false,null)

  //消息内容
  val message="Hello World!"
  /*
    像指定的队列中发送消息：
    String exchange, 交换机
    String routingKey, 路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
    BasicProperties props, 消息属性
    byte[] body 消息内容
   */
  channel.basicPublish("",QUEUE_NAME,null,message.toByteArray(StandardCharsets.UTF_8))
  println("[x] Sent '$message'")
  //关闭通道和连接
  channel.close()
  connection.close()

}