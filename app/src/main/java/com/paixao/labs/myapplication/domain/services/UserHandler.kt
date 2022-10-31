package com.paixao.labs.myapplication.domain.services

import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.User

interface UserHandler {
    suspend fun retrieveUser(userId: String): User

    suspend fun createCharacter(userId: String, newCharacterData: Character)
    suspend fun deleteCharacter(userId: String, characterToDelete: Character)

    suspend fun updateCharacter(
        userId: String,
        newCharacterData: Character,
        oldCharacterData: Character
    )
}
