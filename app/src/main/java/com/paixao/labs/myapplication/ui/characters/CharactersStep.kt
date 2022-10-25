@file:OptIn(ExperimentalMaterialApi::class)

package com.paixao.labs.myapplication.ui.characters

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.ui.sheet.CharacterDetailsScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton
import com.paixao.labs.myapplication.ui.utils.components.Toolbar

@ExperimentalUnitApi
internal object CharactersStep : AndroidScreen() {

    @Composable
    override fun Content() {

        val model = getScreenModel<CharactersModel>()
        val characters = model.characters().collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(model) {
            model.retrieveCharacters()
        }

        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.app_login_back_ground),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        Modifier
                            .fillMaxSize()
                            .scrollable(
                                state = rememberScrollState(),
                                orientation = Orientation.Vertical
                            )
                    ) {
                        Toolbar(title = "Personagens", action = navigator::pop)
                        Spacer(
                            modifier = Modifier.padding(top = Dimens.large)
                        )
                        characters.forEach { character ->
                            CharacterItem(character) {
                                navigator.push(CharacterDetailsScreen(character))
                            }
                        }
                        Spacer(Modifier.weight(1F))

                        PrimaryButton(
                            action = {
                                navigator.push(
                                    CharacterDetailsScreen(
                                        character = Character.new,
                                        isNewCharacter = true
                                    )
                                )
                            },
                            text = "Criar novo personagem"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AndroidScreen.CharacterItem(character: Character, onClick: (Character) -> Unit) {
    val model = getScreenModel<CharactersModel>()
    Card(
        border = BorderStroke(Dimens.strokeSize, color = MaterialTheme.colors.primaryVariant),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.large),
        onClick = { onClick(character) }
    ) {
        Row(modifier = Modifier.padding(horizontal = Dimens.large, vertical = Dimens.xSmall)) {
            Text(text = character.name, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                tint = Color.Black,
                contentDescription = "",
                modifier = Modifier.clickable {
                    model.deleteCharacter(deletedCharacter = character)
                })
        }

    }
}

