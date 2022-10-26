package com.paixao.labs.myapplication.ui.table.tableHome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.ui.table.tableHome.TableHomeScreen.Content
import com.paixao.labs.myapplication.ui.table.tableListing.TableListingScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar

object TableHomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        SheetTheme {
            Surface() {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.app_login_back_ground),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Column(Modifier.fillMaxSize()) {

                        Toolbar(title = "Como vamos prosseguir?")
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TableHomeItem("Entrar em uma mesa", {})
                            TableHomeItem(
                                contentText = "Mestrar uma aventura",
                                action = { navigator?.push(TableListingScreen) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableHomeItem(contentText: String, action: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Dimens.large, vertical = Dimens.xLarge)
        .clickable { action() },
        backgroundColor = MaterialTheme.colors.primary,
        border = BorderStroke(Dimens.strokeSize, color = MaterialTheme.colors.primaryVariant),
        content = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = Dimens.xLarge)
            ) {

                Text(
                    text = contentText,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    Content()
}