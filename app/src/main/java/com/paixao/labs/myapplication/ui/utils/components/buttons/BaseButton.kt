package com.paixao.labs.myapplication.ui.utils.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import com.paixao.labs.myapplication.ui.utils.Dimens
import androidx.compose.ui.graphics.Color

@Composable
internal fun BaseButton(
    modifier: Modifier = Modifier,
    action: () -> Unit,
    text: String,
    wideOption: ButtonWideOption = ButtonWideOption.Wide,
    buttonPresentationOption: ButtonPresentationOption = ButtonPresentationOption.Primary,
) {
    val wideModifiers =
        if (wideOption == ButtonWideOption.Wide) Modifier.fillMaxWidth() else Modifier

    val border = if (buttonPresentationOption == ButtonPresentationOption.Secondary)
        BorderStroke(width = Dimens.strokeSize, color = MaterialTheme.colors.primaryVariant)
    else null

    val textColor =
        if (buttonPresentationOption == ButtonPresentationOption.Primary) Color.Black else
            MaterialTheme.colors.primaryVariant

    SheetTheme() {
        Button(
            modifier = modifier
                .then(wideModifiers)
                .shadow(shape = CircleShape, elevation = Dimens.none),
            onClick = { action() },
            content = {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    text = text,
                    color = textColor
                )
            },
            colors = buttonPresentationOption.colors(),
            shape = CircleShape,
            border = border
        )
    }
}