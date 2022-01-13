package com.paixao.labs.myapplication.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.paixao.labs.myapplication.domain.services.UserHandler

internal class UserAgent(database: FirebaseDatabase) : UserHandler {
    private val firebaseApi = database.getReference("mesa").child("users")

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        firebaseApi.child(userId).get()
}
