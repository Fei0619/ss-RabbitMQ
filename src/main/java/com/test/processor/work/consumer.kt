package com.test.processor.work

/**
 * @author 费世程
 * @date 2019/10/24 9:42
 */
class consumer {
}

fun main() {
  val thread_1=Thread(Runnable {
    run {
      Work_receiver_1.receive()
    }
  })
  val thread_2=Thread(Runnable {
    run {
      Work_receiver_2.receive()
    }
  })

  thread_1.start()
  thread_2.start()

}