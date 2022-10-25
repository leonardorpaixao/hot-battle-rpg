package com.paixao.labs.myapplication.domain.services

import com.paixao.labs.myapplication.domain.models.Table

interface TableHandler {

    suspend fun createTable(userId: String, newTableData: Table)
    suspend fun deleteTable(userId: String, characterToDelete: Table)

    suspend fun updateTable(
        userId: String,
        newTableData: Table,
        oldTableData: Table
    )
}
