package com.paixao.labs.myapplication.ui.table.session

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.paixao.labs.myapplication.domain.services.AdventureSessionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TableSessionScreenModel @Inject constructor(
    private val adventureSessionHandler: AdventureSessionHandler,
) : ScreenModel {

    private val _characterPosition = MutableStateFlow(PlayerPosition.CENTER)

    val characterPosition = _characterPosition.asStateFlow()

    fun updatePlayerPosition(playerPosition: PlayerPosition) {
        coroutineScope.launch {
            adventureSessionHandler.moveCharacter(playerPosition)
        }
    }

    fun retrieveAdventureSession() {
        coroutineScope.launch {
            adventureSessionHandler.loginAdventure().collectLatest {
                _characterPosition.value = it
            }
        }
    }
}