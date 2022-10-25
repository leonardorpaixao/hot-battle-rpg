package com.paixao.labs.myapplication.ui.table.creation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton
import com.paixao.labs.myapplication.ui.utils.components.Toolbar

object CreateTableScreen : Screen {

    @Composable
    override fun Content() {
        val adventureName = remember {
            mutableStateOf("")
        }

        val adventureLore = remember {
            mutableStateOf("")
        }

        val model = getScreenModel<CreateTableScreenModel>()

        SheetTheme() {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {
                Toolbar(title = "Criar nova aventura")
                Spacer(modifier = Modifier.padding(top = Dimens.large))
                Column(
                    Modifier
                        .padding(horizontal = Dimens.large)
                ) {

                    Text(text = "Nome da Aventura: ", Modifier.padding(start = Dimens.xSmall))
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
                                model.updateAdventureName(adventureName.value)
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
                        Modifier.padding(start = Dimens.xSmall, top = Dimens.medium)
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
                        Modifier.padding(start = Dimens.xSmall, top = Dimens.medium)
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
                                    .fillMaxWidth()
                                    .padding(bottom = Dimens.xxxXLarge),
                                value = adventureLore.value,
                                label = null,
                                onValueChange = { newLoreValue ->
                                    adventureLore.value = newLoreValue
                                    model.updateLore(adventureLore.value)
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
                Spacer(modifier = Modifier.weight(1f))
                PrimaryButton(action = { model.createTable() }, text = "Criar nova aventura")
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    CreateTableScreen.Content()
}