package com.paixao.labs.myapplication.data.character

import com.paixao.labs.myapplication.data.models.AttributesResponse
import com.paixao.labs.myapplication.data.models.CharacterResponse
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character

object CharacterRequestMapper {
    operator fun invoke(character: Character): CharacterResponse = character.run {
        CharacterResponse(
            id = id,
            name = name,
            jobClass = jobClass.name,
            level = level,
            race = race.name,
            alignment = alignment,
            attributes = attributes.toRequest(),
        )
    }
}

private fun Attributes.toRequest(): AttributesResponse =
    run {
        AttributesResponse(
            strength = strength.value,
            agility = agility.value,
            constitution = constitution.value,
            intelligence = intelligence.value,
            wisdom = wisdom.value,
            charisma = charisma.value,
        )
    }
