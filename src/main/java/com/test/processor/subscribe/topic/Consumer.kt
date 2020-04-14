package com.test.processor.subscribe.topic

/**
 * @author 费世程
 * @date 2019/10/24 15:09
 */
class Consumer {
}

fun main() {
  val thread_a=Thread(Runnable {
    run {
      Receiver_A.receive()
    }
  })
  val thread_b=Thread(Runnable {
    run {
      Receiver_B.receive()
    }
  })

  thread_a.start()
  thread_b.start()
}