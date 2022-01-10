package com.paixao.labs.myapplication.ui.sheet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class SheetViewModel : ViewModel() {
    private val _names: MutableStateFlow<MutableList<String>> = MutableStateFlow(mutableListOf("Leonardo", "Lueny"))

    fun retrieveNames() = _names.asStateFlow()

    suspend fun addName(newName: String){
        val updatedList = _names.value.apply { add(newName) }
        _names.emit(updatedList)
    }
}