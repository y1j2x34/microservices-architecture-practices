package com.vgerbot.rmi.zk

import org.apache.zookeeper.*
import java.rmi.Naming
import java.rmi.Remote
import java.rmi.registry.LocateRegistry
import java.util.concurrent.CountDownLatch
import java.util.logging.Logger

class ServiceProvider {
    companion object {
        private val LOGGER = Logger.getLogger(ServiceProvider::class.java.name)
    }

    fun publish(remote: Remote, host: String, port: Int): () -> Unit {
        val url = publishService(remote, host, port)
        val zk = connectService()
        createNode(zk, url)
        return {
            Naming.unbind(url)
            zk.delete(Consts.ZK_REGISTRY_PATH, -1)
            zk.close()
        }
    }
    private fun createNode(zk: ZooKeeper, url: String) {
        // 创建一个临时性且有序的 ZNode
        val path = zk.create(Consts.ZK_PROVIDER_PATH, url.toByteArray(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)
        LOGGER.info("create zookeeper node ( $path => $url)")
    }

    private fun publishService(remote: Remote, host: String, port: Int): String {
        val url = "rmi://$host:$port/${remote.javaClass.name}"
        LocateRegistry.createRegistry(port)
        Naming.rebind(url, remote)
        LOGGER.info("publish rmi service (url: $url)")
        return url
    }

    private fun connectService(): ZooKeeper {
        val latch = CountDownLatch(1)
//        val zk = ZooKeeper(Consts.ZK_CONNECTION_STRING, Consts.ZK_SESSION_TIMEOUT,  object : Watcher {
//            override fun process(event: WatchedEvent?) {
//                event ?: return
//                if(event.state == Watcher.Event.KeeperState.SyncConnected ) {
//                        latch.countDown()
//                }
//            }
//        })
        val zk = ZooKeeper(Consts.ZK_CONNECTION_STRING, Consts.ZK_SESSION_TIMEOUT,  Watcher {
            it ?: return@Watcher
            if(it.state == Watcher.Event.KeeperState.SyncConnected ) {
                latch.countDown()
            }
        })
        latch.await()
        return zk
    }
}