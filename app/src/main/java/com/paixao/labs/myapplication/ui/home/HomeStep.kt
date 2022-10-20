package com.paixao.labs.myapplication.ui.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.ui.characters.CharactersStep
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton
import kotlinx.coroutines.launch

@ExperimentalUnitApi
class HomeStep : AndroidScreen() {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = getScreenModel<HomeStepModel>()

        val user = viewModel.listenUser().collectAsState().value

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

                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            Modifier
                                .padding(horizontal = Dimens.xxXLarge, vertical = Dimens.xxXFkLarge)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(Dimens.xSmall)
                                )
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Card(
                                backgroundColor = MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(Dimens.xSmall),
                                border = BorderStroke(
                                    color = MaterialTheme.colors.primaryVariant,
                                    width = Dimens.xxXSmall
                                ),
                                modifier = Modifier.clickable {
                                    navigator.push(CharactersStep(characters = user.content?.characters.orEmpty()))
                                }
                            ) {

                                Text(
                                    text = "Personagens",
                                    modifier = Modifier.padding(50.dp),
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                            }
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        PrimaryButton(action = {
                            scope.launch {
                                Toast.makeText(
                                    context,
                                    "Em desenvolvimento",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }, text = "Entrar numa aventura")
                    }
                }

            }
        }
    }

    @Preview
    @Composable
    fun Preview() {
        Content()
    }

    fun mockedHero() = Character(
        name = "Konnagan",
        jobClass = JobClass.Ranger,
        alignment = "Leal e bom", level = 1,
        race = Race.Human,
        attributes = Attributes(
            strength = 14,
            agility = 18,
            constitution = 12,
            intelligence = 12,
            wisdom = 14,
            charisma = 10
        ),
        id = ""
    )
}