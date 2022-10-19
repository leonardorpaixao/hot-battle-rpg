package com.paixao.labs.myapplication.ui.sheet

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Attributes
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens.DEFAULT_HORIZONTAL
import com.paixao.labs.myapplication.ui.utils.components.PrimaryButton
import com.paixao.labs.myapplication.ui.utils.components.Toolbar
import com.paixao.labs.myapplication.ui.utils.components.TwoTexts
import kotlinx.coroutines.launch

@ExperimentalUnitApi
class CharacterDetailsScreen(private val character: Character) : AndroidScreen() {

    @Composable
    override fun Content() {
        SetupView()
    }

    @Composable
    private fun SetupView() {

        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                Sheet(
                    title = stringResource(
                        R.string.sheet_title,
                        character.name
                    ),
                    characterSheet = character
                )
            }
        }
    }
}

@Composable
fun Sheet(title: String, characterSheet: Character) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                Toolbar(title, navigator::pop)

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DEFAULT_HORIZONTAL)
                        .absolutePadding(top = DEFAULT_HORIZONTAL),
                    value = characterSheet.name,
                    label = { Text(stringResource(id = R.string.sheet_name_label)) },
                    onValueChange = {},
                    readOnly = true,
                    enabled = false
                )

                TwoTexts(
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = DEFAULT_HORIZONTAL
                    ),
                    leftLabel = stringResource(id = R.string.sheet_name_label),
                    leftContent = characterSheet.jobClass.value,
                    rightLabel = stringResource(id = R.string.sheet_race_label),
                    rightContent = characterSheet.race.value
                )
                AttributesField(characterSheet.attributes)
            }
        },
        bottomBar = {
            PrimaryButton(
                action = {
                    scope.launch {
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
    Column(modifier = Modifier.padding(start = 24.dp)) {
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
    Row {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(30.dp),
            text = label
        )
        TextButton(
            onClick = {},
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            enabled = false,
            content = {
                Text(
                    text = content.toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        )
        TextButton(
            onClick = {},
            modifier = Modifier.padding(start = 4.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            enabled = false,
            content = {
                Text(
                    text = ((content - 10) / 2).toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        )
    }
}

@Preview
@Composable
fun Preview() {
    Sheet(
        stringResource(id = R.string.sheet_toolbar_label, "Leonardo"),
        characterSheet = mockedHero()
    )
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
    )
)

