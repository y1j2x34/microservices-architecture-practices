package com.vgerbot.msap.pubref

import com.vgerbot.msap.pubref.entities.User
import com.vgerbot.msap.pubref.services.AccessService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import javax.annotation.Resource

@SpringBootApplication
open class MicroserviceApplication {

    @Resource
    lateinit var service: AccessService

    @EventListener(ApplicationReadyEvent::class)
    fun prepare() {
        service.createUser(User(1, "hello", "world"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MicroserviceApplication::class.java, *args)
}