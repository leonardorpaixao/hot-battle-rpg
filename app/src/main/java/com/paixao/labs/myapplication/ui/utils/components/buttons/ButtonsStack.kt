package com.paixao.labs.myapplication.ui.utils.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.ui.utils.Dimens

private const val SPACE_BETWEEN_BUTTONS = 0.1F

@Composable
fun ButtonStack(
    leftButtonText: String,
    leftButtonAction: () -> Unit,
    rightButtonText: String,
    rightButtonAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large, vertical = 10.dp)
                .then(modifier),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SecondaryButton(
                text = leftButtonText,
                action = leftButtonAction,
                wideOption = ButtonWideOption.Short,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.weight(SPACE_BETWEEN_BUTTONS))
            PrimaryButton(
                text = rightButtonText,
                action = rightButtonAction,
                wideOption = ButtonWideOption.Short,
                modifier = Modifier.weight(1F)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewButtonsStack() {
    ButtonStack(
        "Editar", {},
        "Iniciar Sessão", {},
    )
}
