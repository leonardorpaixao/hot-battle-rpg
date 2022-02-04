package com.paixao.labs.myapplication.ui.utils.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TwoTexts(
    leftLabel: String,
    leftContent: String,
    rightLabel: String,
    rightContent: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            value = leftContent,
            label = { Text(leftLabel) },
            onValueChange = {},
            readOnly = true,
            enabled = false
        )
        Divider(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            value = rightContent,
            label = { Text(rightLabel) },
            onValueChange = {},
            readOnly = true,
            enabled = false
        )
    }
}
