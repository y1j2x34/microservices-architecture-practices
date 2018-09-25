package com.vgerbot.rmi.jndi

import com.vgerbot.rmi.common.HelloServiceImpl

object Consts {
    const val PORT:Int = 9099
    val SERVICE_NAME: String = HelloServiceImpl::class.java.name
}