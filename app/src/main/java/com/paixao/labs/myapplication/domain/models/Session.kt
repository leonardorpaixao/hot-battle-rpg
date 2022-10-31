package com.paixao.labs.myapplication.domain.models

data class Session(
    val user: User
) {
    val currentUserId = user.id
}
