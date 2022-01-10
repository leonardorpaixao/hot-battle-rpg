@file:OptIn(ExperimentalUnitApi::class)

package com.paixao.labs.myapplication.ui.sheet

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.lifecycleScope
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import kotlinx.coroutines.launch

private val viewModel by lazy { SheetViewModel() }

class SheetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SheetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    val names = viewModel.retrieveNames().collectAsState().value
                    Greeting(
                        context = this@SheetActivity,
                        title = getString(R.string.sheet_title, "Leonardo"),
                        names = names
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(context: Context, title: String, names: List<String>) {

        var editTextContent by remember { mutableStateOf("") }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    Toolbar(title)
                    LazyColumn {
                        items(names) { name ->
                            Text(
                                text = name,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .absolutePadding(left = 16.dp, right = 16.dp, top = 24.dp),
                        value = editTextContent,
                        label = { Text("Nome") },
                        onValueChange = { newValue ->
                            editTextContent = newValue
                        }
                    )
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    onClick = {
                        lifecycleScope.launch {
                            viewModel.addName(newName = editTextContent)
                        }
                    },
                    content = {
                        Text(
                            modifier = Modifier.padding(6.dp), text = "Salvar"
                        )
                    },
                    shape = CircleShape
                )
            },
            content = {}
        )
    }
}

private fun handleAddName(context: Context, editTextContent: String, names: MutableList<String>) {
    if (names.contains(editTextContent)) {
        Toast.makeText(context, "Esse nome já existe!", Toast.LENGTH_SHORT).show()
    } else {
        names.add(editTextContent)
    }
}

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
