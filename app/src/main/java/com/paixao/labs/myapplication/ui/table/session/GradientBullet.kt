package com.paixao.labs.myapplication.ui.table.session

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val LIGHT_GREEN = 0xFFBAFFAD
private const val LIGHT_RED = 0xFFFF5252
private const val RADIUS = 10F
private val BULLET_SIZE = 10.dp
private val BULLET_PADDING = 5.dp

object GradientBulletStatus {

    object ONLINE {
        @Composable
        operator fun invoke() {
            Canvas(
                modifier = Modifier
                    .padding(BULLET_PADDING)
                    .size(BULLET_SIZE)
            ) {
                drawCircle(
                    brush = radialGradient(
                        colors = listOf(Color(LIGHT_GREEN), Color.Green),
                        center = Offset.Unspecified,
                        radius = RADIUS
                    )
                )
            }
        }
    }

    object OFFLINE {
        @Composable
        operator fun invoke() {
            Canvas(
                modifier = Modifier
                    .padding(BULLET_PADDING)
                    .size(BULLET_SIZE)
            ) {
                drawCircle(
                    brush = radialGradient(
                        colors = listOf(Color(LIGHT_RED), Color.Red),
                        center = Offset.Unspecified,
                        radius = RADIUS
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewGradientStatusBullet() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column() {
            GradientBulletStatus.ONLINE()
            GradientBulletStatus.OFFLINE()
        }
    }
}
