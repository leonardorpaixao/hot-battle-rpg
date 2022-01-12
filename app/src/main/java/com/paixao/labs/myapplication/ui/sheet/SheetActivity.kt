@file:OptIn(ExperimentalUnitApi::class)

package com.paixao.labs.myapplication.ui.sheet

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.R
import com.paixao.labs.myapplication.domain.Attributes
import com.paixao.labs.myapplication.domain.CharacterSheet
import com.paixao.labs.myapplication.domain.User
import com.paixao.labs.myapplication.ui.theme.SheetTheme
import kotlinx.coroutines.launch

class SheetActivity : ComponentActivity() {
    private val viewModel by lazy { SheetViewModel() }
    private val database = Firebase.database
    private val myRef = database.getReference("mesa").child("users")
    private lateinit var currentUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this.applicationContext)

        setContent {
            var title by remember {
                mutableStateOf("")
            }

            /* myRef.addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(dataSnapshot: DataSnapshot) {
                     val animalList: MutableList<User?> = ArrayList()
                     for (ds in dataSnapshot.children) {
                         val user = ds.getValue<User>()
                         animalList.add(user)
                     }
                     currentUserName = animalList.first()?.nome.orEmpty()
                     Log.d("TAG", "Users retrieved $animalList")
                 }

                 override fun onCancelled(error: DatabaseError) {
                     // Failed to read value
                     Log.w("TAG", "Failed to read value.", error.toException())
                 }
             })*/

            viewModel.listenScreenState().collectAsState().runCatching {
                val state = this.value
                when {
                    state.content != null -> title = state.content.nome
                    state.error != null -> Log.e("Error", state.error.message.orEmpty())
                    else -> {}
                }
            }
            viewModel.retrieveUser()

            SetupView(title)
        }
    }

    @Composable
    private fun SetupView(currentUserName: String) {

        SheetTheme {
            Surface(
                color = MaterialTheme.colors.background
            ) {

                val characterSheet = viewModel.retrieveSheet().collectAsState().value
                Sheet(
                    title = getString(R.string.sheet_title, currentUserName),
                    characterSheet = characterSheet
                )
            }
        }
    }

    @Preview
    @Composable
    fun Preview() {
        Sheet("Ficha de Leonardo", characterSheet = viewModel.mockedHero())
    }

    @Composable
    fun Sheet(title: String, characterSheet: CharacterSheet) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    Toolbar(title)

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .absolutePadding(left = 16.dp, right = 16.dp, top = 24.dp),
                        value = characterSheet.name,
                        label = { Text("Nome") },
                        onValueChange = {},
                        readOnly = true,
                        enabled = false

                    )
                    TwoTexts(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        label1 = "Classe",
                        text1 = characterSheet.jobClass.value,
                        label2 = "Raça",
                        text2 = characterSheet.race.value
                    )
                    Attributes(characterSheet.attributes)
                }
            },
            bottomBar = {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    onClick = {
                        lifecycleScope.launch {
                            Toast.makeText(
                                applicationContext,
                                "Flecha nele!!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val teste = User(classe = "Mago", nome = "Thomaz")
                        myRef.child("users").child("Leo").setValue(teste)
                    },
                    content = {
                        Text(
                            modifier = Modifier.padding(6.dp), text = "Atacar"
                        )
                    },
                    shape = CircleShape
                )
            },
            content = {}
        )
    }
}

@Composable
fun Attributes(attributes: Attributes) {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        AttributeRow("For ", attributes.strength)
        AttributeRow("Dex", attributes.dexterity)
        AttributeRow("Con", attributes.constitution)
        AttributeRow("Int   ", attributes.intelligence)
        AttributeRow("Sab ", attributes.wisdom)
        AttributeRow("Car  ", attributes.charisma)
    }
}

@Composable
fun AttributeRow(
    label: String,
    content: Int
) {
    Row() {
        Text(
            modifier = Modifier
                .align(CenterVertically)
                .padding(end = 4.dp),
            text = label
        )
        TextButton(
            onClick = {},
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            enabled = false,
            content = {
                Text(
                    text = content.toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        )
        TextButton(
            onClick = {},
            modifier = Modifier.padding(start = 4.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            enabled = false,
            content = {
                Text(
                    text = ((content - 10) / 2).toString(),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        )
    }
}

@Composable
fun TwoTexts(
    label1: String,
    text1: String,
    label2: String,
    text2: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            value = text1,
            label = { Text(label1) },
            onValueChange = {},
            readOnly = true,
            enabled = false
        )
        Divider(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxHeight()
                .width(6.dp)
        )
        TextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            value = text2,
            label = { Text(label2) },
            onValueChange = {},
            readOnly = true,
            enabled = false
        )
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
                .align(CenterVertically)
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
