package com.paixao.labs.myapplication.ui.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.sheet.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class HomeStepModel @Inject constructor(
    private val userHandler: UserHandler
) : ScreenModel {

    private val _user: MutableStateFlow<UserState> = MutableStateFlow(UserState())

    fun listenUser() = _user.asStateFlow()

    init {
        retrieveUser()
    }

    fun retrieveUser(): Unit {
        _user.value = _user.value.copy(isLoading = true)
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    userHandler.retrieveUser("0")
                }.fold(
                    onSuccess = { userResult ->
                        _user.emit(_user.value.copy(isLoading = false, content = userResult))
                    },
                    onFailure = { error ->
                        _user.emit(
                            _user.value.copy(isLoading = false, error = error)
                        )
                    }
                )

            }
        }

    }

}


data class HomeState(
    val content: User? = null,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)