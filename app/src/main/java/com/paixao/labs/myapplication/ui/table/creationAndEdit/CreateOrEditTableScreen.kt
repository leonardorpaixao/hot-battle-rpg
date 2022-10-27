package com.paixao.labs.myapplication.ui.table.creationAndEdit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.ui.table.tableDetails.TableDetailsScreen
import com.paixao.labs.myapplication.ui.table.tableListing.TableListingScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

data class CreateOrEditTableScreen(val table: Table? = null) : Screen {
    @Composable
    override fun Content() {
        val model = getScreenModel<CreateOrEditTableScreenModel>()
        val tableResult = model.tableCreationState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(table) {
            if (table != null) model.setupModel(table)
        }

        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            if (tableResult.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.xxXLarge),
                    color = MaterialTheme.colors.primaryVariant
                )
            }

            if (tableResult.createdTable != null) {
                navigator.pop()
                navigator.push(TableDetailsScreen(tableResult.createdTable))
                model.resetStates()
            }

            if (tableResult.updatedTable != null) {
                navigator.popUntil { screen -> screen is TableListingScreen }
                navigator.push(TableDetailsScreen(tableResult.updatedTable))
                model.resetStates()
            }

            CreateOrEditContent(
                table = table,
                updateAdventureName = model::updateAdventureName,
                updateLore = model::updateLore,
                createTable = { model.createTable() },
                updateTable = { table?.let { oldTable -> model.updateTable(oldTable) } },
            )
        }
    }
}

@Composable
private fun CreateOrEditContent(
    table: Table? = null,
    updateAdventureName: (String) -> Unit,
    updateLore: (String) -> Unit,
    createTable: () -> Unit,
    updateTable: () -> Unit,
) {
    val navigator = LocalNavigator.currentOrThrow

    val isOnEditFLow = remember(table) {
        table != null
    }

    val adventureName = remember(table) {
        mutableStateOf(table?.adventureName ?: "")
    }

    val adventureLore = remember {
        mutableStateOf(table?.lore ?: "")
    }

    val toolbarTitle = remember(isOnEditFLow) {
        if (isOnEditFLow) "Editar aventura" else "Criar nova aventura"
    }

    val buttonText = remember(isOnEditFLow) {
        if (isOnEditFLow) "Salvar mudanças" else "Continuar"
    }

    SheetTheme() {
        Surface() {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())

                ) {
                    Toolbar(title = toolbarTitle, action = { navigator.pop() })
                    Spacer(modifier = Modifier.padding(top = Dimens.large))
                    Column(
                        Modifier
                            .padding(horizontal = Dimens.large)
                            .padding(bottom = 70.dp)
                    ) {

                        Text(
                            text = "Nome da Aventura: ",
                            Modifier.padding(start = Dimens.xSmall),
                            fontWeight = FontWeight.Medium
                        )
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            modifier = Modifier
                                .padding(2.dp)
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = adventureName.value,
                                label = null,
                                onValueChange = { newName ->
                                    adventureName.value = newName
                                    updateAdventureName(adventureName.value)
                                },
                                shape = RoundedCornerShape(4.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.Black,
                                    disabledLabelColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                            )
                        }

                        Text(
                            text = "Sistema: ",
                            Modifier.padding(start = Dimens.xSmall, top = Dimens.medium),
                            fontWeight = FontWeight.Medium
                        )
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            modifier = Modifier
                                .padding(2.dp)
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = "D&D 3.5",
                                label = null,
                                onValueChange = { },
                                enabled = false,
                                shape = RoundedCornerShape(4.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    cursorColor = Color.Black,
                                    disabledLabelColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                            )
                        }

                        Text(
                            text = "História: ",
                            Modifier.padding(start = Dimens.xSmall, top = Dimens.medium),
                            fontWeight = FontWeight.Medium
                        )
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            modifier = Modifier
                                .padding(2.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Top
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = adventureLore.value,
                                    label = null,
                                    onValueChange = { newLoreValue ->
                                        adventureLore.value = newLoreValue
                                        updateLore(adventureLore.value)
                                    },
                                    shape = RoundedCornerShape(4.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.White,
                                        cursorColor = Color.Black,
                                        disabledLabelColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                )
                            }
                        }
                    }
                }
                PrimaryButton(
                    action = { if (table == null) createTable() else updateTable() },
                    text = buttonText
                )
            }
        }
    }

}

@Composable
@Preview
private fun Preview() {
    val table = Table(
        adventureName = "Culto de Baidu",
        adventureSystem = "D&D 3.5",
        lore = "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32." +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."
    )
    CreateOrEditContent(
        table = table,
        updateAdventureName = {},
        updateLore = {},
        createTable = {},
        updateTable = {},
    )
}