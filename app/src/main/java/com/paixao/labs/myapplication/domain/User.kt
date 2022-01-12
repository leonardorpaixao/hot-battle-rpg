package com.paixao.labs.myapplication.domain

data class Response(
    val users: List<User>? = null
)

data class User(
    val classe: String = "",
    val nome: String = ""
)
