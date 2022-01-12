package com.paixao.labs.myapplication.domain

data class ScreenState<T>(
    val content: T? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)
