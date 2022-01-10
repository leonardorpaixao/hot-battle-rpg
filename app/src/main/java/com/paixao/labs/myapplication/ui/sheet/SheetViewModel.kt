package com.paixao.labs.myapplication.ui.sheet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SheetViewModel : ViewModel() {
    private val _names: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf("Leonardo", "Lueny", "Caique"))

    fun retrieveNames() = _names.asStateFlow()

    suspend fun addName(newName: String) {
        val updatedList = _names.value.toMutableList()
        updatedList.add(newName)
        _names.emit(updatedList)
    }
}
