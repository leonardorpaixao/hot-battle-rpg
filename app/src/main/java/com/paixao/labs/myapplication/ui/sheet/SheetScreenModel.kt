package com.paixao.labs.myapplication.ui.sheet

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.models.ScreenState
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SheetScreenModel @Inject constructor(
    private val userHandler: UserHandler
) : ScreenModel {

    private val _characterSheet: MutableStateFlow<Character> =
        MutableStateFlow(mockedHero())

    private val _screenState: MutableStateFlow<ScreenState<User>> =
        MutableStateFlow(ScreenState(isLoading = true))

    private val _user: MutableStateFlow<UserState> = MutableStateFlow(UserState())

    fun editCharacter(): Unit {
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

    fun mockedHero() = Character(
        name = "Konnagan",
        jobClass = JobClass.Ranger,
        alignment = "Leal e bom", level = 1,
        race = Race.Human,
        attributes = Attributes(
            strength = 14,
            agility = 18,
            constitution = 12,
            intelligence = 12,
            wisdom = 14,
            charisma = 10
        )
    )
}

data class UserState(
    val content: User? = null,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
