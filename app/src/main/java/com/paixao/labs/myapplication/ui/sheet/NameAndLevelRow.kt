package com.paixao.labs.myapplication.ui.utils.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Character


private val ONLY_NUMBERS_REGEX = "[^0-9]".toRegex()
private const val EMPTY = ""

@Composable
fun NameAndLevelRow(
    character: Character,
    modifier: Modifier = Modifier,
    leftOnValueChange: (String) -> Unit = {},
    rightOnValueChange: (String) -> Unit = {},
) {
    var name by remember { mutableStateOf(character.name) }
    var level by remember { mutableStateOf(character.level.toString()) }


    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            modifier = Modifier
                .padding(2.dp)
                .weight(1F)
        ) {
            TextField(
                value = name,
                label = { Text(stringResource(id = R.string.sheet_name_label)) },
                onValueChange = { newName ->
                    name = newName
                    leftOnValueChange(newName)
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
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            modifier = Modifier
                .padding(2.dp)
                .weight(1F)

        ) {
            TextField(
                modifier = Modifier,
                value = level.toString(),
                label = { Text(stringResource(id = R.string.sheet_level_label)) },
                onValueChange = { newLevel ->
                    level = if (newLevel.isNotBlank()) {
                        rightOnValueChange(newLevel)
                        newLevel
                    } else ""
                },
                visualTransformation = LevelFieldRule,
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

private object LevelFieldRule : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = if (text.isNotBlank()) AnnotatedString(
            text.replace(ONLY_NUMBERS_REGEX, ""
            )
        ) else AnnotatedString(EMPTY)

        return TransformedText(formattedText,
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = offset
                override fun transformedToOriginal(offset: Int): Int = offset
            }
        )

    }

}
