package com.paixao.labs.myapplication.ui.utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Toolbar(title: String, action: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "back button",
            modifier =
            Modifier
                .align(Alignment.CenterVertically)
                .clickable(role = Role.Button) { action() },

        )
        Text(
            text = title,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 10.dp),
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(value = 20F, TextUnitType.Sp)
        )
    }
}
