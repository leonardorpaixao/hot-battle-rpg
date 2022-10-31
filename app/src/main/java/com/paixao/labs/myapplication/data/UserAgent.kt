package com.paixao.labs.myapplication.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.paixao.labs.myapplication.data.UserMapper.toDomain
import com.paixao.labs.myapplication.data.character.CharacterRequestMapper
import com.paixao.labs.myapplication.data.models.UserResponse
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.util.Date

internal class UserAgent(
    private val firebaseFireStore: FirebaseFirestore,
) : UserHandler {

    override suspend fun retrieveUser(userId: String): User {

        val userResult = runCatching {
            firebaseFireStore
                .collection(MAIN_COLLECTION)
                .document(userId)
                .get()
                .await()
                .toObject<UserResponse>()
                ?.toDomain()
        }

        return userResult.fold(
            onSuccess = { it ?: error(ERROR_MESSAGE) },
            onFailure = { error(it) }
        )
    }

    override suspend fun createCharacter(userId: String, newCharacterData: Character) {
        val characterId = Date.from(Instant.now()).time.toString()
        val characterRequest = CharacterRequestMapper(newCharacterData.copy(id = characterId))
        firebaseFireStore.collection(MAIN_COLLECTION)
            .document(userId)
            .update(mapOf(CHARACTERS_OBJ_REF to FieldValue.arrayUnion(characterRequest)))
            .await()
    }

    /**To remove some object in an array, we have to send a similar reference of the object that we want to delete**/
    override suspend fun deleteCharacter(userId: String, characterToDelete: Character) {
        val characterRequest = CharacterRequestMapper(characterToDelete)
        firebaseFireStore.collection(MAIN_COLLECTION)
            .document(userId)
            .update(mapOf(CHARACTERS_OBJ_REF to FieldValue.arrayRemove(characterRequest)))
            .await()
    }

    override suspend fun updateCharacter(
        userId: String,
        newCharacterData: Character,
        oldCharacterData: Character
    ) {
        val characterReference = firebaseFireStore.collection(MAIN_COLLECTION)
            .document(userId)

        val updatedCharacterRequest = CharacterRequestMapper(newCharacterData)

        val removeCharacterRequest = CharacterRequestMapper(oldCharacterData)

        characterReference
            .update(mapOf(CHARACTERS_OBJ_REF to FieldValue.arrayRemove(removeCharacterRequest)))
            .await()

        characterReference
            .update(mapOf(CHARACTERS_OBJ_REF to FieldValue.arrayUnion(updatedCharacterRequest)))
            .await()
    }

    private companion object {
        const val ERROR_MESSAGE = "User not found"
        const val MAIN_COLLECTION = "users"
        const val CHARACTERS_OBJ_REF = "characters"
    }
}
