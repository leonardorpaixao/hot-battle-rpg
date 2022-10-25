package com.paixao.labs.myapplication.ui.table.creation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.domain.services.SessionHandler
import com.paixao.labs.myapplication.domain.services.TableHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class CreateTableScreenModel @Inject constructor(
    private val sessionHandler: SessionHandler,
    private val tableHandler: TableHandler,
) : ScreenModel {

    private var _newTable = Table(
        adventureSystem = "D&D 3.5"
    )

    private var _tableCreationState = MutableStateFlow<TableCreationState>(TableCreationState())

    fun updateAdventureName(newAdventureName: String) {
        _newTable = _newTable.copy(adventureName = newAdventureName)
    }

    fun updateLore(newLore: String) {
        _newTable = _newTable.copy(lore = newLore)
    }

    fun createTable() {

        runBlocking {
            coroutineScope.launch {
                _tableCreationState.emit(_tableCreationState.value.copy(isLoading = true))

                runCatching {
                    val userId = sessionHandler.getCurrentSession().currentUserId
                    val preparedTableToPush = _newTable.copy(
                        masterId = userId
                    )
                    tableHandler.createTable(userId, preparedTableToPush)
                    sessionHandler.updateSession()
                }.fold(
                    onSuccess = {
                        _tableCreationState.emit(
                            _tableCreationState.value.copy(
                                isLoading = false,
                                success = true
                            )
                        )
                    },
                    onFailure = { error ->
                        _tableCreationState.emit(
                            _tableCreationState.value.copy(
                                isLoading = false,
                                success = false,
                                error = error
                            )
                        )
                    }
                )
            }
        }
    }
}

data class TableCreationState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: Throwable? = null
)