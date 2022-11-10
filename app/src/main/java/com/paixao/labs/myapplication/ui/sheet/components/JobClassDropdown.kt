package com.paixao.labs.myapplication.ui.sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.models.Character
import com.paixao.labs.myapplication.domain.models.JobClass

@Composable
fun JobClassDropdown(
    character: Character,
    onSelected: (JobClass) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedJobClass by remember { mutableStateOf(character.jobClass) }
    val jobClasses = remember { JobClass.values() }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black),
            modifier = Modifier
                .padding(2.dp)
                .clickable {
                    expanded = true
                }
        ) {
            TextField(
                value = selectedJobClass.value,
                label = { Text(stringResource(id = R.string.sheet_class_label)) },
                onValueChange = {},
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
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .fillMaxWidth(),
            offset = DpOffset(x = 50.dp, y = 0.dp)
        ) {
            jobClasses.forEach { jobClass ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedJobClass = jobClass
                        onSelected(jobClass)
                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = jobClass.value,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
