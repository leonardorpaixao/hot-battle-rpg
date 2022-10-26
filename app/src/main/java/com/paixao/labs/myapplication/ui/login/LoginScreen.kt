package com.paixao.labs.myapplication.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.LoginResultState
import com.paixao.labs.myapplication.ui.home.HomeStep
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

@ExperimentalUnitApi
class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val model = getScreenModel<LoginScreenModel>()
        SetupView(model)
    }

    @Composable
    private fun SetupView(model: LoginScreenModel) {
        val navigator = LocalNavigator.currentOrThrow
        val loginState = model.retrieveLoginStatus().collectAsState().value

        if (loginState.success != null)
            navigator.push(HomeStep())


        SheetTheme {
            Surface(
                color = MaterialTheme.colors.onPrimary
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = { ScreenContent(loginState) },
                    bottomBar = {
                        PrimaryButton(
                            action = {
                                model.login()
                            },
                            text = "Login",
                            isBackGroundInvisible = true
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(loginResultState: LoginResultState) {
    SheetTheme() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.app_login_back_ground),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            if (loginResultState.isLoading)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(Dimens.xxXLarge),
                        color = MaterialTheme.colors.primaryVariant
                    )
                }

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Card(
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.xxXLarge)
                        .padding(top = Dimens.xxXFkLarge),
                    contentColor = MaterialTheme.colors.primaryVariant,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo), contentDescription = "",
                        modifier = Modifier.background(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    ScreenContent(loginResultState = LoginResultState())
}
