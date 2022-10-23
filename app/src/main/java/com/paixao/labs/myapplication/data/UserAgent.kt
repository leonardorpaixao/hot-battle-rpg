package com.paixao.labs.myapplication.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
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
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseFireStore: FirebaseFirestore,
) : UserHandler {
    private val firebaseApi = firebaseDatabase.getReference(MAIN_PATH).child(PATH_SECONDARY)

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        firebaseApi.child(userId).get()

    override suspend fun retrieveUser(userId: String): User {

        val userResult = runCatching {
            firebaseFireStore
                .collection("users")
                .document(userId)
                .get()
                .await()
                .toObject<UserResponse>()
                ?.toDomain()
        }

        return userResult.fold(
            onSuccess = { it ?: error("User not found") },
            onFailure = { error(it) }
        )
    }

    override suspend fun createCharacter(userId: String, newCharacterData: Character) {
        val characterId = Date.from(Instant.now()).time.toString()
        val characterRequest = CharacterRequestMapper(newCharacterData.copy(id = characterId))
        firebaseFireStore.collection(PATH_SECONDARY)
            .document(userId)
            .update(mapOf("characters" to FieldValue.arrayUnion(characterRequest)))
            .await()
    }

    /**Para remover um objeto de um array, basta que passemos a referencia exata daquele objeto**/
    override suspend fun deleteCharacter(userId: String, characterToDelete: Character) {
        val characterRequest = CharacterRequestMapper(characterToDelete)
        firebaseFireStore.collection(PATH_SECONDARY)
            .document(userId)
            .update(mapOf("characters" to FieldValue.arrayRemove(characterRequest)))
            .await()
    }

    override suspend fun updateCharacter(
        userId: String,
        newCharacterData: Character,
        oldCharacterData: Character
    ) {
        val characterReference = firebaseFireStore.collection(PATH_SECONDARY)
            .document(userId)

        val updatedCharacterRequest = CharacterRequestMapper(newCharacterData)

        val removeCharacterRequest = CharacterRequestMapper(oldCharacterData)


        characterReference
            .update(mapOf("characters" to FieldValue.arrayRemove(removeCharacterRequest)))
            .await()

        characterReference
            .update(mapOf("characters" to FieldValue.arrayUnion(updatedCharacterRequest)))
            .await()

        //
        println(characterReference)
    }

    private companion object {
        const val MAIN_PATH = "mesa"
        const val PATH_SECONDARY = "users"
    }
}



