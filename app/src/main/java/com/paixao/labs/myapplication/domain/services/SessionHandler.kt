package com.paixao.labs.myapplication.domain.services

import com.paixao.labs.myapplication.domain.models.LoginResultState
import com.paixao.labs.myapplication.domain.models.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal interface SessionHandler {
    suspend fun getCurrentSession(): Session
    suspend fun login(): Flow<LoginResultState>
    suspend fun logout()
    suspend fun updateSession()
}