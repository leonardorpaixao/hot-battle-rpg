package com.paixao.labs.myapplication.domain.services

import com.paixao.labs.myapplication.ui.table.session.PlayerPosition
import kotlinx.coroutines.flow.Flow

interface AdventureSessionHandler {

    fun loginAdventure(): Flow<PlayerPosition>
    fun moveCharacter(position: PlayerPosition)
}
