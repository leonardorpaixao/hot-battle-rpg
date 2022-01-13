package com.paixao.labs.myapplication.domain.services

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface UserHandler {
    suspend fun retrieveChampion(userId: String): Task<DataSnapshot>
}
