package com.paixao.labs.myapplication.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.paixao.labs.myapplication.domain.services.UserHandler

internal class UserAgent(private val firebaseDatabase: FirebaseDatabase) : UserHandler {
    private val firebaseApi = firebaseDatabase.getReference(MAIN_PATH).child(PATH_SECONDARY)

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        firebaseApi.child(userId).get()

    private companion object {
        const val MAIN_PATH = "mesa"
        const val PATH_SECONDARY = "users"
    }
}
