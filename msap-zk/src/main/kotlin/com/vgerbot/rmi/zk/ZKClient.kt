package com.vgerbot.rmi.zk

import com.vgerbot.rmi.common.HelloService

fun main(args: Array<String>) {
    val customer = ServiceCustomer()
    for(i in 0 .. 10) {
        Thread {
            run {
                while (true) {
                    try {
                        customer.lookup<HelloService> {
                            val ret = it.sayHello("zookeeper client-${Thread.currentThread().name}")
                            println("server >>> $ret")
                        }
                    } catch (e: Exception){
                        println("Oh! no!")
                        e.printStackTrace()
                    }
                    Thread.sleep(3000)
                }
            }
        }.start()
    }
    Thread.sleep(Long.MAX_VALUE)
}