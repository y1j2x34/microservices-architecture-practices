package com.vgerbot.rmi.zk

import com.vgerbot.rmi.common.HelloServiceImpl

fun main(args: Array<String>) {
    val port = if (args.isEmpty()) 1099 else args[0].toInt()
    val provider = ServiceProvider()
    val service = HelloServiceImpl(port)
    val unpublish = provider.publish(service, "localhost", port)
//    Thread.sleep(5000)
    unpublish()
}
