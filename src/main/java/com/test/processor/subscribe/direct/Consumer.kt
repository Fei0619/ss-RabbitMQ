package com.test.processor.subscribe.direct

/**
 * @author 费世程
 * @date 2019/10/24 14:29
 */
class Consumer {
}

fun main() {

  val thread_A=Thread(Runnable {
    run {
      Receiver_A.receive()
    }
  })
  val thread_B=Thread(Runnable {
    run {
      Receiver_B.receive()
    }
  })

  thread_A.start()
  thread_B.start()

}