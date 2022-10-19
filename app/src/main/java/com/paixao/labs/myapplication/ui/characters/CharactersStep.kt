package com.paixao.labs.myapplication.ui.characters

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.ui.sheet.CharacterDetailsScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton

@ExperimentalUnitApi
internal data class CharactersStep(private val characters: List<Character>) : AndroidScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .scrollable(
                            state = rememberScrollState(),
                            orientation = Orientation.Vertical
                        )
                ) {
                    characters.forEach {
                        PrimaryButton(action = {
                            navigator.push(CharacterDetailsScreen(it))
                        }, text = it.name)
                    }
                }

            }
        }
    }
}