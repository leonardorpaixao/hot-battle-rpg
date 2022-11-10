package com.paixao.labs.myapplication.domain.models

internal data class LoginResultState(
    val isLoading: Boolean = false,
    val success: User? = null,
    val error: Throwable? = null
)
