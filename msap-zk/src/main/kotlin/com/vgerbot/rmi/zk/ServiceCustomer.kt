package com.vgerbot.rmi.zk

import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.rmi.Naming
import java.rmi.Remote
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import java.util.logging.Logger

class ServiceCustomer {
    companion object {
        private val LOGGER = Logger.getLogger(ServiceCustomer::class.qualifiedName)
    }
    private val latch = CountDownLatch(1)
    private var urlList: List<String> = mutableListOf<String>()

    init {
        val zk = connectServer()
        watchNode(zk)
    }

    fun <T:Remote> lookup(callback: (service:T) -> Unit) {
        if (urlList.isNotEmpty()) {
            val size = urlList.size
            val url = if (size == 1) urlList[0] else urlList[ThreadLocalRandom.current().nextInt(size)]
            LOGGER.info("using url: $url")
            @Suppress("UNCHECKED_CAST")
            callback(Naming.lookup(url) as T)
        }
    }

    private fun connectServer(): ZooKeeper{
        val zk = ZooKeeper(Consts.ZK_CONNECTION_STRING, Consts.ZK_SESSION_TIMEOUT, Watcher { event ->
            event ?: return@Watcher
            if(event.state === Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown()
            }
        })
        latch.await()
        return zk
    }

    private fun watchNode(zk: ZooKeeper) {
        val nodeList = zk.getChildren(Consts.ZK_REGISTRY_PATH, Watcher {
            event ->
                event ?: return@Watcher
            when(event.type) {
                Watcher.Event.EventType.NodeChildrenChanged -> watchNode(zk)
                else -> {
                    // IGNORED
                }
            }
        })
        urlList = nodeList.map {
            val data = zk.getData("${Consts.ZK_REGISTRY_PATH}/$it", false, null)
            String(data)
        }
    }
}