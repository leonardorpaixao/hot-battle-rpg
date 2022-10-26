package com.paixao.labs.myapplication.ui.utils.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paixao.labs.myapplication.ui.utils.Dimens

private const val SPACE_BETWEEN_BUTTONS = 0.15F

@Composable
fun ButtonStack(
    leftButtonText: String,
    leftButtonAction: () -> Unit,
    rightButtonText: String,
    rightButtonAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimens.large, vertical = Dimens.small)
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row() {
            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                action = { leftButtonAction() },
                text = leftButtonText,
                wideOption = ButtonWideOption.Short,
                buttonPresentationOption = ButtonPresentationOption.Secondary
            )
            Spacer(modifier = Modifier.weight(SPACE_BETWEEN_BUTTONS))
            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                action = { rightButtonAction() },
                text = rightButtonText,
                wideOption = ButtonWideOption.Short,
                buttonPresentationOption = ButtonPresentationOption.Primary
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
