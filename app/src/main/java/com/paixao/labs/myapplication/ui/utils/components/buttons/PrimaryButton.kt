package com.paixao.labs.myapplication.ui.utils.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.ui.utils.Dimens

@Composable
fun PrimaryButton(
    text: String,
    action: () -> Unit,
    modifier: Modifier? = null,
    wideOption: ButtonWideOption = ButtonWideOption.Wide,
) {

    BaseButton(
        modifier = modifier ?: Modifier.padding(horizontal = Dimens.large, vertical = 10.dp),
        text = text,
        action = { action() },
        wideOption = wideOption,
        buttonPresentationOption = ButtonPresentationOption.Primary,
    )
}

@Preview
@Composable
private fun PreviewPrimaryButton() {
    PrimaryButton("Primary Button", {})
}