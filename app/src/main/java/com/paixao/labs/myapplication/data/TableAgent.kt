package com.paixao.labs.myapplication.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.domain.services.TableHandler
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.util.Date

internal class TableAgent(
    private val firebaseFireStore: FirebaseFirestore,
) : TableHandler {


    override suspend fun createTable(userId: String, newTableData: Table) {
        val tableId = Date.from(Instant.now()).time.toString()
        val tableRequest = newTableData.copy(tableCode = tableId)
        firebaseFireStore
            .collection(COLLECTION)
            .document(userId)
            .set(
                mapOf(MASTER_FIELD to mapOf(TABLES_FIELD to FieldValue.arrayUnion(tableRequest))),
                SetOptions.merge()
            )
            .await()
    }

    override suspend fun deleteTable(userId: String, tableToDelete: Table) {
        firebaseFireStore
            .collection(COLLECTION)
            .document(userId)
            .set(
                mapOf(MASTER_FIELD to mapOf(TABLES_FIELD to FieldValue.arrayUnion(tableToDelete))),
                SetOptions.merge()
            )
            .await()
    }

    override suspend fun updateTable(userId: String, newTableData: Table, oldTableData: Table) {
        deleteTable(userId = userId, tableToDelete = oldTableData)
        createTable(userId = userId, newTableData = newTableData)
    }

    private companion object {
        const val TABLES_FIELD = "tables"
        const val MASTER_FIELD = "master"
        const val COLLECTION = "users"
    }
}



