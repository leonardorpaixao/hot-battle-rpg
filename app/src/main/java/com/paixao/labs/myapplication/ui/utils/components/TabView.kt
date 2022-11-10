package com.paixao.labs.myapplication.ui.utils.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.ui.utils.components.buttons.PrimaryButton

@OptIn(ExperimentalUnitApi::class)

object TabViewManager {

    @Composable
    operator fun invoke() {
        TabRow(selectedTabIndex = 1) {
        }

        PrimaryButton(
            action = {},
            text = "Experimental"
        )
    }
}

data class TabViewModel(
    val icon: TabIcon
)

object TabIcon {

    @Composable
    operator fun invoke(text: String, @DrawableRes icon: Int = R.drawable.ic_back) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(icon),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = text,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TabIcon("TabView", R.drawable.ic_back)
}
