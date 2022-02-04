package com.paixao.labs.myapplication.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.ui.sheet.SheetScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton

@ExperimentalUnitApi
class LoginScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        SetupView()
    }

    @Composable
    fun SetupView() {
        val navigator = LocalNavigator.currentOrThrow
        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    content = { ScreenContent() },
                    bottomBar = {
                        PrimaryButton(
                            action = {
                                navigator.push(SheetScreen())
                            },
                            text = "Login"
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ScreenContent() {
    var content by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.login_table_code_hint)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.DEFAULT_HORIZONTAL)
                .absolutePadding(top = Dimens.DEFAULT_HORIZONTAL)
                .background(Color.White),
            value = content,
            onValueChange = { content = it },
            enabled = true,
            textStyle = TextStyle(textAlign = TextAlign.Center)
        )
    }
}

@Composable
@Preview
fun Preview2() {
    ScreenContent()
}
