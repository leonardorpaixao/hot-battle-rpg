package com.paixao.labs.myapplication.domain.services

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.paixao.labs.myapplication.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserHandler {
    suspend fun retrieveChampion(userId: String): Task<DataSnapshot>
    suspend fun retrieveUser(userId: String): User
}
