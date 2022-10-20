package com.paixao.labs.myapplication.ui.sheet

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import com.paixao.labs.myapplication.ui.utils.components.NameAndLevelRow
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import kotlinx.coroutines.launch

@ExperimentalUnitApi
class CharacterDetailsScreen(private val character: Character) : AndroidScreen() {

    @Composable
    override fun Content() {
        SetupView()
    }

    @Composable
    private fun SetupView() {
        val viewModel = getScreenModel<CharacterDetailsScreenModel>()

        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                Sheet(
                    title = stringResource(
                        R.string.sheet_title,
                        character.name
                    ),
                    character = character,
                    model = viewModel
                )
            }
        }
    }
}


@Composable
fun Sheet(title: String, character: Character, model: CharacterDetailsScreenModel) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(character) {
        model.updateCharacterChanges(character = character)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Toolbar(title, navigator::pop)
                NameAndLevelRow(
                    modifier = Modifier.padding(
                        vertical =  Dimens.xSmall,
                        horizontal = Dimens.xLarge
                    ),
                    character = character,
                    leftOnValueChange = { newName -> model.updateCharacterName(newName) },
                    rightOnValueChange = { newLevel -> model.updateCharacterLevel(newLevel) },

                    )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.xLarge)
                ) {
                    JobClassDropdown(
                        modifier = Modifier.weight(1F),
                        character = character,
                        onSelected = { jobClass ->
                            model.updateJobClass(jobClass)
                        })

                    RaceDropdown(
                        modifier = Modifier.weight(1F),
                        character = character,
                        onSelected = { race ->
                            model.updateRace(race)
                        })

                }

                AttributesField(character.attributes)
            }
        },
        bottomBar = {
            PrimaryButton(
                action = {
                    scope.launch {
                        model.editCharacter(character)
                        Toast.makeText(
                            context,
                            context.getText(R.string.sheet_changes_was_saved_toast_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                text = stringResource(id = R.string.sheet_save_label)
            )
        },
        content = {}
    )
}

@Composable
fun AttributesField(attributes: Attributes) {
    Text(
        modifier = Modifier
            .padding(horizontal = Dimens.xLarge)
            .padding(top = Dimens.medium, bottom = Dimens.xSmall),
        text = "Atributos",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )
    Column(modifier = Modifier.padding(start = Dimens.xLarge)) {
        AttributeRow(stringResource(id = R.string.sheet_str_label), attributes.strength)
        AttributeRow(stringResource(id = R.string.sheet_dex_label), attributes.agility)
        AttributeRow(stringResource(id = R.string.sheet_con_label), attributes.constitution)
        AttributeRow(stringResource(id = R.string.sheet_int_label), attributes.intelligence)
        AttributeRow(stringResource(id = R.string.sheet_win_label), attributes.wisdom)
        AttributeRow(stringResource(id = R.string.sheet_car_label), attributes.charisma)
    }
}

@Composable
fun AttributeRow(
    label: String,
    content: Int
) {
    var attrState by remember { mutableStateOf(content.toString()) }

    Row {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(30.dp),
            text = label
        )
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            modifier = Modifier.padding(2.dp)

        ) {
            TextField(
                modifier = Modifier
                    .background(Color.White)
                    .width(70.dp),
                label = null,
                value = attrState,
                onValueChange = { attrState = it },
                enabled = true,
                textStyle = TextStyle(textAlign = TextAlign.Center),
                singleLine = true,
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
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            modifier = Modifier.padding(2.dp)

        ) {
            TextField(
                modifier = Modifier
                    .background(Color.White)
                    .width(70.dp),
                label = null,
                value = ((attrState.toInt() - 10) / 2).toString(),
                onValueChange = { },
                enabled = false,
                textStyle = TextStyle(textAlign = TextAlign.Center, color = Color.Black),
                singleLine = true,
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

@Preview
@Composable
fun Preview() {
    Sheet(
        stringResource(id = R.string.sheet_toolbar_label, "Leonardo"),
        character = mockedHero(),
        model = CharacterDetailsScreenModel(MockHandler)
    )
}

object MockHandler : UserHandler {
    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> {TODO()}
    override suspend fun retrieveUser(userId: String): User { TODO() }
    override suspend fun createCharacter(userId: String, newCharacterData: Character) {}
    override suspend fun updateCharacter(
        userId: String,
        newCharacterData: Character,
        oldCharacterData: Character
    ) {}
    override suspend fun deleteCharacter(userId: String, newCharacterData: Character) {}
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

