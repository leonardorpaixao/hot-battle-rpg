package com.paixao.labs.myapplication.ui.table.tableDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Table
import com.paixao.labs.myapplication.ui.table.creationAndEdit.CreateOrEditTableScreen
import com.paixao.labs.myapplication.ui.table.session.TableSessionScreen
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.buttons.ButtonStack

private const val SPACER_WEIGHT = 0.25F

internal data class TableDetailsScreen(val table: Table) : Screen {

    @Composable
    override fun Content() {
        TableDetails(table = table)
    }
}

@Composable
private fun TableDetails(table: Table) {
    val navigator = LocalNavigator.currentOrThrow
    SheetTheme() {
        Surface {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 70.dp)
                ) {
                    Toolbar(title = "Detalhes da aventura")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.LightGray)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(Dimens.large)
                                .background(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colors.background
                                ),
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Imagem da aventura"
                        )
                    }

                    Row(modifier = Modifier.testTag("nameAndSystemTextViews")) {
                        Column(Modifier.weight(1f)) {

                            Text(
                                text = "Nome da aventura",
                                modifier = Modifier.padding(
                                    top = Dimens.large,
                                    start = Dimens.large
                                ),
                                fontSize = 14.sp
                            )

                            Text(
                                text = table.adventureName,
                                modifier = Modifier.padding(
                                    top = Dimens.xxXSmall,
                                    start = Dimens.large,
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.weight(SPACER_WEIGHT))

                        Column(
                            Modifier
                                .padding(end = Dimens.large)
                                .weight(1f)
                        ) {

                            Text(
                                text = "Sistema da mesa",
                                modifier = Modifier.padding(top = Dimens.large),
                                fontSize = 14.sp
                            )

                            Text(
                                text = table.adventureSystem,
                                modifier = Modifier.padding(
                                    top = Dimens.xxXSmall,
                                    end = Dimens.large,
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Text(
                        text = "História",
                        modifier = Modifier.padding(top = Dimens.large, start = Dimens.large),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = table.lore,
                        modifier = Modifier
                            .padding(top = Dimens.xxXSmall)
                            .padding(horizontal = Dimens.large),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )


                }
                ButtonStack(
                    leftButtonText = "Editar",
                    leftButtonAction = { navigator.push(CreateOrEditTableScreen(table)) },
                    rightButtonText = "Iniciar Sessão",
                    rightButtonAction = { navigator.push(TableSessionScreen(table)) },
                )
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    val table = Table(
        adventureName = "O Culto de Baidu",
        adventureSystem = "D&D 3.5",
        lore = "What is Lorem Ipsum?\n" +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                "\n" +
                "Why do we use it?\n" +
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
                "\n" +
                "\n" +
                "Where does it come from?\n" +
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham."
    )
    TableDetails(table = table)
}