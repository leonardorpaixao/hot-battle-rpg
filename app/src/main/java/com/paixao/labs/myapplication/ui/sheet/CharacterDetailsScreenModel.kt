package com.paixao.labs.myapplication.ui.sheet

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class CharacterDetailsScreenModel @Inject constructor(
    private val userHandler: UserHandler
) : ScreenModel {

    private var _characterSheet: Character? = null

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

    fun updateAttribute(newAttributes: Attributes) {
        _characterSheet = _characterSheet?.copy(attributes = newAttributes)
    }


    fun editCharacter(character: Character): Unit {
        if (character == _characterSheet) return
        runBlocking {
            coroutineScope.launch {
                runCatching {

                    _characterSheet?.let {
                        userHandler.updateCharacter(
                            "0",
                            _characterSheet!!,
                            character
                        )
                    }
                }.fold(
                    onSuccess = { userResult ->
                    },
                    onFailure = { error ->

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
        ),
        id = ""
    )
}

data class CharacterSheetState(
    val content: Character,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

