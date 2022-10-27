package com.paixao.labs.myapplication.ui.table.session

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TableSessionScreenModel @Inject constructor() : ScreenModel {

    private val _characterPosition = MutableStateFlow(PlayerPosition.CENTER)

    val characterPosition = _characterPosition.asStateFlow()


    fun updatePlayerPosition(playerPosition: PlayerPosition) {
        coroutineScope.launch {
            _characterPosition.emit(playerPosition)
        }
    }


}