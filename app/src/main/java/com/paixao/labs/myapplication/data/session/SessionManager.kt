package com.paixao.labs.myapplication.data.session

import android.util.Log
import com.paixao.labs.myapplication.domain.models.LoginResultState
import com.paixao.labs.myapplication.domain.models.Session
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val USER_ID = "0"

internal class SessionManager(val userHandler: UserHandler) : SessionHandler {

    private var _session: Session? = null

    override suspend fun getCurrentSession(): Session =
        _session ?: error("session does not exist")

    override suspend fun login(): Flow<LoginResultState> = flow {
        emit(LoginResultState(isLoading = true))

        runCatching {
            userHandler.retrieveUser(USER_ID)
        }.fold(
            onSuccess = { user ->
                _session = Session(user = user)
                emit(LoginResultState(isLoading = false, success = user))
            },
            onFailure = { error ->
                emit(LoginResultState(isLoading = false, error = error))
            }
        )
    }

    override suspend fun logout() {
        _session = null
    }

    override suspend fun updateSession() {
        runCatching { userHandler.retrieveUser(USER_ID) }.fold(
            onSuccess = {
                _session = _session?.copy(user = it)
            },
            onFailure = { error ->
                Log.e("error on update login", "$error")
            }
        )
    }
}