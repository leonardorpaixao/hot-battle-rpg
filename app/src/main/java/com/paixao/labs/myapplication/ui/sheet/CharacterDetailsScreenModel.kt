package com.paixao.labs.myapplication.ui.sheet

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Attribute
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


internal class CharacterDetailsScreenModel @Inject constructor(
    private val userHandler: UserHandler,
    private val sessionHandler: SessionHandler,
) : ScreenModel {

    private var _characterSheet: Character? = null
    private var _oldCharacter: Character? = null

    fun updateCharacterChanges(character: Character) {
        _characterSheet = character
    }


    fun updateCharacterName(newName: String) {
        _characterSheet = _characterSheet?.copy(name = newName)
    }

    fun updateCharacterLevel(newLevel: String) {
        _characterSheet = _characterSheet?.copy(level = newLevel.toInt())
    }

    fun updateJobClass(newJobClass: JobClass) {
        _characterSheet = _characterSheet?.copy(jobClass = newJobClass)
    }

    fun updateRace(newRace: Race) {
        _characterSheet = _characterSheet?.copy(race = newRace)
    }

    fun updateAttribute(newAttribute: String, content: Attribute) {
        val adjustedAttr = if (newAttribute.isBlank()) "0" else newAttribute
        when (content) {
            is Attribute.Strength -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        strength = Attribute.Strength(
                            (adjustedAttr.toInt())
                        )
                    )
                )

            is Attribute.Agility -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        agility = Attribute.Agility(
                            (adjustedAttr.toInt())
                        )
                    )
                )


            is Attribute.Constitution -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        constitution = Attribute.Constitution((adjustedAttr.toInt()))
                    )
                )

            is Attribute.Intelligence -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        intelligence = Attribute.Intelligence((adjustedAttr.toInt()))
                    )
                )


            is Attribute.Wisdom -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        wisdom = Attribute.Wisdom(
                            (adjustedAttr.toInt())
                        )
                    )
                )

            is Attribute.Charisma -> _characterSheet =
                _characterSheet?.copy(
                    attributes = _characterSheet?.attributes!!.copy(
                        charisma = Attribute.Charisma(
                            (adjustedAttr.toInt())
                        )
                    )
                )
        }
    }


    fun saveCharacter(oldCharacter: Character, isNewCharacter: Boolean) {
        if (isNewCharacter) createNewCharacter()
        else editCharacter(oldCharacter)
    }

    fun editCharacter(oldCharacter: Character): Unit {
        if ((_oldCharacter ?: oldCharacter) == _characterSheet) return
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    val session = sessionHandler.getCurrentSession()
                    val editedCharacter = _characterSheet

                    if (editedCharacter != null) {
                        userHandler.updateCharacter(
                            userId = session.currentUserId,
                            newCharacterData = editedCharacter,
                            oldCharacterData = _oldCharacter ?: oldCharacter
                        )
                    }
                }.fold(
                    onSuccess = { userResult ->
                        sessionHandler.updateSession()
                    },
                    onFailure = { error ->

                    }
                )

            }
        }
    }

    private fun createNewCharacter(): Unit {
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    val session = sessionHandler.getCurrentSession()
                    val newCharacter = _characterSheet

                    if (newCharacter != null) {
                        userHandler.createCharacter(
                            userId = session.currentUserId,
                            newCharacterData = newCharacter,
                        )
                    }
                }.fold(
                    onSuccess = { userResult ->
                        sessionHandler.updateSession()
                    },
                    onFailure = { error ->

                    }
                )

            }
        }

    }
}


