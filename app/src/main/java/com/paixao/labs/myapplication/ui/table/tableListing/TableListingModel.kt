package com.paixao.labs.myapplication.ui.table.tableListing

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

internal class TableListingModel @Inject constructor(
    private val sessionHandler: SessionHandler,
    private val tableHandler: TableHandler,
) : ScreenModel {
    private var _tables: MutableStateFlow<TableListingState> =
        MutableStateFlow(TableListingState(isLoading = true))
    val tables = _tables.asStateFlow()

    fun retrieveTables() {
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    val session = sessionHandler.getCurrentSession()
                    if (session.user.master?.tables?.isNotEmpty() == true) {
                        _tables.value = TableListingState(
                            isLoading = false,
                            isEmpty = false,
                            tables = session.user.master.tables,
                            error = null
                        )
                    } else {
                        _tables.value = TableListingState(
                            isLoading = false,
                            isEmpty = true,
                        )
                    }
                }.fold(
                    onSuccess = {},
                    onFailure = { TODO() }
                )
            }
        }
    }

    fun deleteTable(tableToDelete: Table) {
        runBlocking {
            coroutineScope.launch {
                runCatching {
                    val userId = sessionHandler.getCurrentSession().currentUserId
                    tableHandler.deleteTable(userId = userId, characterToDelete = tableToDelete)
                }.fold(
                    onSuccess = {
                        _tables.value = _tables.value.copy(
                            tables = _tables.value.tables.filterNot { table ->
                                table == tableToDelete
                            }
                        )
                        sessionHandler.updateSession()
                    },
                    onFailure = { error ->
                        Log.e("error when deleting table -> $error", error.message.toString())
                    }
                )
            }
        }
    }
}

internal data class TableListingState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val tables: List<Table> = emptyList(),
    val error: Throwable? = null,
)