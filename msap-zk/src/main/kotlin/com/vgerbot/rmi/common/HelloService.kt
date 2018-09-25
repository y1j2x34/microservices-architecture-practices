package com.vgerbot.rmi.common

import java.rmi.Remote
import java.rmi.RemoteException
import java.rmi.server.UnicastRemoteObject

interface HelloService : Remote {
    @Throws(RemoteException::class)
    fun sayHello(name: String): String
}

class HelloServiceImpl() : UnicastRemoteObject(), HelloService {
    var serverPort: Int = -1
    constructor(port: Int) : this() {
        serverPort = port
    }
    override fun sayHello(name: String): String {
        println("server#${this.serverPort} hello $name")
        return "server#${this.serverPort} saied hello $name"
    }
}