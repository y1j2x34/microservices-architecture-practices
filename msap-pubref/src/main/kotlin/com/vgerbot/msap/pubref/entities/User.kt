package com.vgerbot.msap.pubref.entities

data class User(
        var id: Int
) {
    var name: String? = null
    var password: String? = null

    constructor(id: Int, name: String, password: String) : this(id) {
        this.name = name
        this.password = password
    }
}