package com.paixao.labs.myapplication.ui.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.LoginResultState
import com.paixao.labs.myapplication.domain.services.SessionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


internal class LoginScreenModel @Inject constructor(
    private val sessionManager: SessionHandler
) : ScreenModel {

    private val _loginStatus = MutableStateFlow(LoginResultState(isLoading = false))

    fun retrieveLoginStatus() = _loginStatus.asStateFlow()

    fun login() {
        runBlocking {
            coroutineScope.launch {
                sessionManager.login().collectLatest { loginResultState ->
                    _loginStatus.emit(loginResultState)
                }
            }
        }
    }
}
