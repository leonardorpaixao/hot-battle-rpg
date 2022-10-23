package com.paixao.labs.myapplication.data

import com.paixao.labs.myapplication.data.models.AttributesResponse
import com.paixao.labs.myapplication.data.models.CharacterResponse
import com.paixao.labs.myapplication.data.models.MasterResponse
import com.paixao.labs.myapplication.data.models.TableResponse
import com.paixao.labs.myapplication.data.models.UserResponse
import com.paixao.labs.myapplication.domain.models.Attribute
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Master
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.domain.models.User

object UserMapper {

    @JvmName("toDomainUserResponse")
    fun UserResponse.toDomain(): User = this.let { response ->
        User(
            id = response.id,
            name = response.name,
            characters = response.characters.toDomain(),
            master = response.master?.toDomain(),
        )
    }

    @JvmName("toDomainCharacterResponse")
    private fun List<CharacterResponse>.toDomain(): List<Character> = map { characterResponse ->
        characterResponse.run {
            Character(
                id = id,
                name = name,
                jobClass = JobClass.valueOf(jobClass),
                level = level,
                race = Race.valueOf(race),
                alignment = alignment,
                attributes = attributes.toDomain(),
            )
        }
    }

    @JvmName("toDomainMasterResponse")
    private fun MasterResponse.toDomain(): Master = Master(
        id = id,
        name = name,
        tables = tables.toDomain()
    )

    @JvmName("toDomainTableResponse")
    private fun List<TableResponse>.toDomain(): List<Table> = map { tableResponse ->
        Table(
            id = tableResponse.id,
            adventureName = tableResponse.adventureName
        )
    }

}

private fun AttributesResponse.toDomain(): Attributes = run {
    Attributes(
        strength = Attribute.Strength(strength),
        agility = Attribute.Agility(agility),
        constitution = Attribute.Constitution(constitution),
        intelligence = Attribute.Intelligence(intelligence),
        wisdom = Attribute.Wisdom(wisdom),
        charisma = Attribute.Charisma(charisma),
    )
}
