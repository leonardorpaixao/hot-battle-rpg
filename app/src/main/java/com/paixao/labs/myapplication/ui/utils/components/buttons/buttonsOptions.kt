package com.paixao.labs.myapplication.ui.utils.components.buttons

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

enum class ButtonWideOption {
    Wide,
    Short
}

internal enum class ButtonPresentationOption(val colors: @Composable () -> ButtonColors) {
    Primary(
        colors =
        @Composable
        {
            ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
            )
        }
    ),
    Secondary(
        colors =
        @Composable {
            ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            )
        }
    )
}
