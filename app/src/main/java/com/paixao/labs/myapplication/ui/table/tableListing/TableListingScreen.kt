@file:OptIn(ExperimentalMaterialApi::class)

package com.paixao.labs.myapplication.ui.table.tableListing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.ui.table.creationAndEdit.CreateOrEditTableScreen
import com.paixao.labs.myapplication.ui.table.tableDetails.TableDetailsScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

object TableListingScreen : Screen {

    @Composable
    override fun Content() {
        SheetTheme() {
            Surface {
                TableListingContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Screen.TableListingContent() {
    val model = getScreenModel<TableListingModel>()
    val navigator = LocalNavigator.currentOrThrow
    val tables = model.tables.collectAsState().value

    val toolbarTitle = remember(tables) {
        if (!tables.isEmpty) "Escolha uma aventura" else "Aventuras"
    }

    LaunchedEffect(model) {
        model.retrieveTables()
    }

    Column(Modifier.fillMaxSize()) {
        Toolbar(title = toolbarTitle)
        Spacer(modifier = Modifier.padding(top = Dimens.large))
        if (!tables.isEmpty && !tables.isLoading)
            tables.tables.forEach { table ->
                TableItem(table)
            }
        // else TODO(Create EmptyState() screen)

        Spacer(Modifier.weight(1F))
        PrimaryButton(
            action = { navigator.push(CreateOrEditTableScreen()) },
            text = "Criar aventura"
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun Screen.TableItem(table: Table) {
    val model = getScreenModel<TableListingModel>()
    val navigator = LocalNavigator.currentOrThrow

    Card(
        border = BorderStroke(Dimens.strokeSize, color = MaterialTheme.colors.primaryVariant),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.large),
        onClick = { navigator.push(TableDetailsScreen(table)) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Dimens.large, vertical = Dimens.xSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = table.adventureName, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.weight(1F))

            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                tint = Color.Black,
                contentDescription = "",
                modifier = Modifier.clickable {
                    model.deleteTable(table)
                })
        }
    }
}