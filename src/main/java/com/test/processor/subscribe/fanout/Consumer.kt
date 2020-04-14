package com.test.processor.subscribe.fanout

/**
 * @author 费世程
 * @date 2019/10/24 9:36
 */
class Consumer {
}

/**
 * 执行结果：
 * 同一个消息被多个消费者获取
 * 一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费到消息
 */
fun main() {
  val thread_1=Thread(Runnable {
    run {
      Subscribe_receiver_a.receive()
    }
  })
  val thread_2=Thread(Runnable {
    run {
      Subscribe_receiver_b.receive()
    }
  })

  thread_1.start()
  thread_2.start()

}