package com.paixao.labs.myapplication.ui.utils.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.ui.utils.Dimens.DEFAULT_HORIZONTAL

@Composable
fun PrimaryButton(
    action: () -> Unit,
    text: String
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DEFAULT_HORIZONTAL),

        onClick = {
            action()
        },
        content = {
            Text(modifier = Modifier.padding(6.dp), text = text)
        },
        shape = CircleShape,
    )
}
