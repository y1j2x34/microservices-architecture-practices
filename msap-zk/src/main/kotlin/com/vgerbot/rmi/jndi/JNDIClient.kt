package com.vgerbot.rmi.jndi

import com.vgerbot.rmi.common.HelloService
import java.rmi.Naming

fun main(args: Array<String>) {
    val url = "rmi://localhost:${Consts.PORT}/${Consts.SERVICE_NAME}"
    val service =Naming.lookup(url) as HelloService
    val ret = service.sayHello("Jack!")
    println(ret)
}