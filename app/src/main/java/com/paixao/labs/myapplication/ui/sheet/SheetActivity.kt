package com.paixao.labs.myapplication.ui.sheet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalUnitApi::class)
@ExperimentalFoundationApi
@AndroidEntryPoint
class SheetActivity : ComponentActivity() {


    private val myRef by lazy { Firebase.database.getReference("mesa").child("users") }
    private lateinit var currentUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this.applicationContext)
        myRef.addValueEventListener(object : ValueEventListener {
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
        })

        setContent {
            //Navigator(screen = SheetScreen())
        }
    }
}
