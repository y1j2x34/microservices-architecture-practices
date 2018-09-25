package com.vgerbot.rmi.zk

object Consts {
    val ZK_CONNECTION_STRING = "localhost:2181"
    val ZK_SESSION_TIMEOUT = 5000
    val ZK_REGISTRY_PATH = "/registry"
    val ZK_PROVIDER_PATH = "$ZK_REGISTRY_PATH/provider"
}