package com.test.util

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

/**
 * @author 费世程
 * @date 2019/10/22 13:55
 */
class ConnectionUtil {

  companion object {
    /**
     * 获取MQ的连接
     */
    fun getConnection(): Connection {
      //定义连接工厂
      val factory = ConnectionFactory()
      //服务地址
      factory.host = "127.0.0.1"
      //端口
      factory.port = 5672
      //设置账号信息：用户名、密码、vhost
      factory.virtualHost = "test_host"
      factory.username = "feishicheng"
      factory.password = "Fei@sc"
      //通过工程获取连接
      return factory.newConnection()
    }
  }

}