@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class,
    ExperimentalUnitApi::class
)

package com.paixao.labs.myapplication.ui.characters

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.ui.sheet.CharacterDetailsScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

@ExperimentalUnitApi
internal object CharactersScreen : Screen {

    @Composable
    override fun Content() {

        val model = getScreenModel<CharactersScreenModel>()
        val characters = model.characters().collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(model) {
            model.retrieveCharacters()
        }
        CharactersContent(
            characters = characters,
            deleteCharacter = model::deleteCharacter
        )
    }
}

@Composable
private fun CharactersContent(
    characters: List<Character>,
    deleteCharacter: (Character) -> Unit,

) {
    val navigator = LocalNavigator.current

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
                Toolbar(title = "Personagens", action = { navigator?.pop() })
                Spacer(
                    modifier = Modifier.padding(top = Dimens.large)
                )
                characters.forEach { character ->
                    CharacterItem(
                        character = character,
                        onClick = { navigator?.push(CharacterDetailsScreen(character)) },
                        deleteCharacter = { characterToDelete ->
                            deleteCharacter(characterToDelete)
                        }
                    )
                }
                Spacer(Modifier.weight(1F))

                PrimaryButton(
                    action = {
                        navigator?.push(
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

@Composable
private fun CharacterItem(
    character: Character,
    onClick: (Character) -> Unit,
    deleteCharacter: (Character) -> Unit
) {
    Card(
        border = BorderStroke(Dimens.strokeSize, color = MaterialTheme.colors.primaryVariant),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.large, vertical = Dimens.xxXSmall),
        onClick = { onClick(character) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Dimens.large, vertical = Dimens.xSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = character.name, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                tint = Color.Black,
                contentDescription = "delete character",
                modifier = Modifier
                    .padding(Dimens.xXSmall)
                    .shadow(elevation = Dimens.strokeSize, shape = RoundedCornerShape(50.dp))
                    .clickable {
                        deleteCharacter(character)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCharactersScreen() {
    val characters = listOf(Character(name = "ReLL1k", id = ""), Character(name = "Lylie", id = ""))
    CharactersContent(characters = characters, deleteCharacter = {})
}
