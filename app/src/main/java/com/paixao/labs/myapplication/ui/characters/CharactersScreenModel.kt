package com.paixao.labs.myapplication.ui.characters

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class CharactersScreenModel @Inject constructor(
    private val sessionHandler: SessionHandler,
    private val userHandler: UserHandler,
) : ScreenModel {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())

    fun characters() = _characters.asStateFlow()

    fun retrieveCharacters() {
        runBlocking {
            coroutineScope.launch {
                val characters = sessionHandler.getCurrentSession().user.characters
                _characters.emit(characters)
            }
        }
    }

    fun deleteCharacter(deletedCharacter: Character) {
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    val session = sessionHandler.getCurrentSession()

                    userHandler.deleteCharacter(
                        userId = session.currentUserId,
                        characterToDelete = deletedCharacter,
                    )
                }.fold(
                    onSuccess = { userResult ->
                        _characters.emit(
                            _characters.value.filterNot { character ->
                                character == deletedCharacter
                            }
                        )
                        sessionHandler.updateSession()
                    },
                    onFailure = { error ->
                    }
                )
            }
        }
    }
}
