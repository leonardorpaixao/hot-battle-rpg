package com.paixao.labs.myapplication.ui.table.creationAndEdit

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.TableHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class CreateOrEditTableScreenModel @Inject constructor(
    private val sessionHandler: SessionHandler,
    private val tableHandler: TableHandler,
) : ScreenModel {

    private var _newTable = Table(
        adventureSystem = "D&D 3.5"
    )

    private var _lastChangedTable = Table()

    private var _tableCreationState = MutableStateFlow(TableEditOrCreationScreenState())

    val tableCreationState = _tableCreationState.asStateFlow()

    fun updateAdventureName(newAdventureName: String) {
        _newTable = _newTable.copy(adventureName = newAdventureName)
    }

    fun updateLore(newLore: String) {
        _newTable = _newTable.copy(lore = newLore)
    }

    fun resetStates() {
        runBlocking {
            coroutineScope.launch {
                _tableCreationState.emit(TableEditOrCreationScreenState())
            }
        }
    }

    fun createTable() {
        runBlocking {
            coroutineScope.launch {
                _tableCreationState.emit(_tableCreationState.value.copy(isLoading = true))
                val userId = sessionHandler.getCurrentSession().currentUserId
                val preparedTableToPush = _newTable.copy(masterId = userId)

                runCatching {

                    tableHandler.createTable(userId, preparedTableToPush)
                    sessionHandler.updateSession()
                }.fold(
                    onSuccess = {
                        _tableCreationState.emit(
                            _tableCreationState.value.copy(
                                isLoading = false,
                                createdTable = preparedTableToPush
                            )
                        )
                    },
                    onFailure = { error ->
                        Log.e("Error to create table --> $error", error.message.toString())

                        _tableCreationState.emit(
                            _tableCreationState.value.copy(
                                isLoading = false,
                                createdTable = null,
                                error = error
                            )
                        )
                    }
                )
            }
        }
    }

    fun setupModel(table: Table) {
        _newTable = table
    }

    fun updateTable(oldTableData: Table) {
        val lastTableData =
            if (oldTableData != _lastChangedTable) oldTableData else _lastChangedTable
        runBlocking {
            coroutineScope.launch {
                val userId = sessionHandler.getCurrentSession().currentUserId

                runCatching {
                    tableHandler.updateTable(
                        userId = userId,
                        newTableData = _newTable,
                        oldTableData = lastTableData
                    )
                }.fold(
                    onSuccess = {
                        _lastChangedTable = _newTable
                        sessionHandler.updateSession()
                        _tableCreationState.emit(
                            _tableCreationState.value.copy(
                                isLoading = false,
                                updatedTable = _newTable
                            )
                        )
                    },
                    onFailure = { error ->
                        Log.e("Error to update table --> $error", error.message.toString())
                    }
                )
            }
        }
    }
}

data class TableEditOrCreationScreenState(
    val isLoading: Boolean = false,
    val createdTable: Table? = null,
    val updatedTable: Table? = null,
    val error: Throwable? = null
)
