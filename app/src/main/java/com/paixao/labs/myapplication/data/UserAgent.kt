package com.paixao.labs.myapplication.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.paixao.labs.myapplication.data.UserMapper.toDomain
import com.paixao.labs.myapplication.data.models.UserResponse
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import kotlinx.coroutines.tasks.await

internal class UserAgent(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseFireStore: FirebaseFirestore
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

    private companion object {
        const val MAIN_PATH = "mesa"
        const val PATH_SECONDARY = "users"
    }
}



