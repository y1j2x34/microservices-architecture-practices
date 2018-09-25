package com.vgerbot.msap.pubref.services

import com.vgerbot.msap.pubref.entities.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AccessService {
    fun createUser(user: User): Boolean {
        logger.info("user created: id=${user.id}, name=${user.name}")
        return true
    }
    companion object {
        val logger = LoggerFactory.getLogger(AccessService::class.java)!!
    }
}