package com.paixao.labs.myapplication.ui.table.session

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.buttons.ButtonWideOption
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

data class TableSessionScreen(val table: Table) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model = getScreenModel<TableSessionScreenModel>()

        val direction = model.characterPosition.collectAsState().value

        LaunchedEffect(model) {
            model.retrieveAdventureSession()
        }

        TableSessionScreenContent(
            table = Table(),
            onlineCharacters = emptyList(),
            offlineCharacters = emptyList(),
            navigateBack = { navigator.pop() },
            playerPosition = direction,
            changePlayerPosition = { playerPosition ->
                model.updatePlayerPosition(playerPosition = playerPosition)
            },
        )
    }
}

@Composable
private fun TableSessionScreenContent(
    table: Table,
    onlineCharacters: List<Character>,
    offlineCharacters: List<Character>,
    navigateBack: () -> Unit,
    playerPosition: PlayerPosition,
    changePlayerPosition: (PlayerPosition) -> Unit,
) {

    SheetTheme() {
        Surface() {
            Column(Modifier.fillMaxSize()) {
                Toolbar(title = table.adventureName, action = { navigateBack() })
                PlayersStatus(
                    masterName = table.masterName,
                    onlineCharacters = onlineCharacters,
                    isMasterOnline = true,
                    offLineCharacters = offlineCharacters,
                    playerPosition = playerPosition,
                    changePlayerPosition = changePlayerPosition
                )
            }
        }
    }
}

@Composable
private fun PlayersStatus(
    masterName: String,
    isMasterOnline: Boolean,
    onlineCharacters: List<Character>,
    offLineCharacters: List<Character>,
    playerPosition: PlayerPosition,
    changePlayerPosition: (PlayerPosition) -> Unit,
) {
    @DrawableRes
    val characterImageRes = remember(playerPosition) {
        when (playerPosition) {
            PlayerPosition.LEFT -> R.drawable.ic_warrior_left
            PlayerPosition.CENTER -> R.drawable.ic_warrior_center
            PlayerPosition.RIGHT -> R.drawable.ic_warrior_right
        }
    }

    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isMasterOnline) GradientBulletStatus.ONLINE() else GradientBulletStatus.OFFLINE()
            Text(text = "GM: $masterName")
        }
        onlineCharacters.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientBulletStatus.ONLINE()
                Text(text = it.name)
            }
        }

        offLineCharacters.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GradientBulletStatus.OFFLINE()
                Text(text = it.name)
            }
        }
        Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = characterImageRes), contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large, vertical = Dimens.large),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryButton(
                modifier = Modifier,
                text = "Esquerda",
                action = { changePlayerPosition(PlayerPosition.LEFT) },
                wideOption = ButtonWideOption.Short
            )
            PrimaryButton(
                modifier = Modifier,
                text = "Centro",
                action = { changePlayerPosition(PlayerPosition.CENTER) },
                wideOption = ButtonWideOption.Short
            )
            PrimaryButton(
                modifier = Modifier,
                text = "Direita",
                action = { changePlayerPosition(PlayerPosition.RIGHT) },
                wideOption = ButtonWideOption.Short
            )
        }
    }
}

@Composable
@Preview
private fun TableSessionPreview() {
    TableSessionScreenContent(
        table = Table(masterName = "Thomaz", adventureName = "O Culto de Baidu"),
        onlineCharacters = listOf(
            Character(name = "ReLL1k", id = ""),
            Character(name = "Sakuragi", id = "")
        ),
        offlineCharacters = listOf(
            Character(name = "Kanda", id = ""),
            Character(name = "Iury", id = ""),
            Character(name = "Carlos", id = "")
        ),
        navigateBack = {},
        playerPosition = PlayerPosition.CENTER,
        changePlayerPosition = {},
    )
}

internal data class AdventureSession(
    val table: Table,
    val isMasterOnline: Boolean,
    val onlineCharacters: List<Character> = emptyList(),
    val offLineCharacters: List<Character> = emptyList()
)
