package com.paixao.labs.myapplication.ui.table.tableLogin

import cafe.adriel.voyager.core.model.ScreenModel
import com.paixao.labs.myapplication.domain.models.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TableLoginScreenModel @Inject constructor(): ScreenModel {

    private val _tableState = MutableStateFlow<TableLoginState>(TableLoginState(isLoading = true))
    val tableState = _tableState.asStateFlow()

    fun loginOnTable(tableCode: String, tablePassword: String) {
        //TODO()
    }
}

data class TableLoginState(
    val isLoading: Boolean,
    val success: Table? = null,
    val error: Throwable? = null
)

