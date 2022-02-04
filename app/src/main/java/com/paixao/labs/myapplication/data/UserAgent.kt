package com.paixao.labs.myapplication.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.domain.services.UserHandler
import javax.inject.Inject

class UserAgent @Inject constructor() : UserHandler {
    private val firebase = Firebase
    private val firebaseApi = firebase.database.getReference("mesa").child("users")

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        firebaseApi.child(userId).get()
}
