package com.vgerbot.rmi.jndi

import com.vgerbot.rmi.common.HelloServiceImpl
import java.rmi.Naming
import java.rmi.registry.LocateRegistry

fun main(args: Array<String>) {
    val port = Consts.PORT
    val url = "rmi://localhost:$port/${Consts.SERVICE_NAME}"
    LocateRegistry.createRegistry(port)
    Naming.rebind(url, HelloServiceImpl())
    println("server started")
}