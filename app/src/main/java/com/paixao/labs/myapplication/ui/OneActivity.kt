package com.paixao.labs.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.navigator.Navigator
import com.paixao.labs.myapplication.ui.sheet.SheetScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalUnitApi::class)
@ExperimentalFoundationApi
@AndroidEntryPoint
class OneActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigator(screen = SheetScreen())
        }
    }
}
