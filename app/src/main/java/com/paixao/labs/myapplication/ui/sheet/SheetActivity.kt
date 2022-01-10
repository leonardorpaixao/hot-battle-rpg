@file:OptIn(ExperimentalUnitApi::class)

package com.paixao.labs.myapplication.ui.sheet

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.flow.collectLatest

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


                    lifecycleScope.launchWhenStarted {
                        viewModel.retrieveNames().collectLatest {  names ->
                            Greeting(
                                context = this@SheetActivity,
                                title = getString(R.string.sheet_title, "Leonardo"),
                                names = names
                            )
                        }
                    }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(context: Context, title: String, names: MutableList<String>) {
    val names by remember {
        mutableStateOf(mutableListOf("Leonardo", "Lueny"))
    }
    var editTextContent by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
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
            TextField(value = editTextContent, onValueChange = { newValue ->
                editTextContent = newValue
            },
                label = { Text("Nome") })
        }
    },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                onClick = {
                    names.add("teste")/*handleAddName(context, editTextContent, names)*/
                }, content = {
                    Text(
                        modifier = Modifier.padding(6.dp), text = "Salvar"
                    )
                },
                shape = CircleShape
            )
        },
        content = {})
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
