package com.paixao.labs.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.paixao.labs.myapplication.ui.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalUnitApi::class)
@ExperimentalFoundationApi
@AndroidEntryPoint
class StartActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigator(screen = LoginScreen()) { navigator ->
                FadeTransition(navigator)
            }
        }
    }
}
