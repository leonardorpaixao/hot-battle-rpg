package com.paixao.labs.myapplication.domain.services

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.User

interface UserHandler {
    suspend fun retrieveChampion(userId: String): Task<DataSnapshot>
    suspend fun retrieveUser(userId: String): User

    suspend fun createCharacter(userId: String, newCharacterData: Character)
    suspend fun deleteCharacter(userId: String, newCharacterData: Character)

    suspend fun updateCharacter(
        userId: String,
        newCharacterData: Character,
        oldCharacterData: Character
    )
}
