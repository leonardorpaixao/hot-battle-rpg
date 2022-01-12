package com.paixao.labs.myapplication.ui.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.paixao.labs.myapplication.domain.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

internal class SheetViewModel : ViewModel() {
    private val _characterSheet: MutableStateFlow<CharacterSheet> =
        MutableStateFlow(mockedHero())

    private val _screenState: MutableStateFlow<ScreenState<User>> =
        MutableStateFlow(ScreenState(isLoading = true))

    private val userHandler = UserAgent()

    fun retrieveSheet() = _characterSheet.asStateFlow()

    fun listenScreenState() = _screenState.asStateFlow()

    fun retrieveUser() {
        runBlocking {
            viewModelScope.launch {
                _screenState.emit(ScreenState<User>(isLoading = true))
                userHandler.retrieveChampion("Leo").addOnCompleteListener {
                    val user = it.result?.getValue<User>()
                    viewModelScope.launch {
                        if (user != null) {
                            _screenState.emit(
                                ScreenState(
                                    content = user,
                                    error = null,
                                    isLoading = false
                                )
                            )
                        } else {
                            _screenState.emit(
                                ScreenState(
                                    content = null,
                                    error = IllegalArgumentException("User not found"),
                                    isLoading = false
                                )
                            )
                        }
                    }
                }
                    .addOnFailureListener { exception ->
                        viewModelScope.launch {
                            _screenState.emit(
                                ScreenState(
                                    content = null,
                                    error = exception,
                                    isLoading = false
                                )
                            )
                        }
                    }
            }
        }
    }

    fun mockedHero() = CharacterSheet(
        name = "Konnagan",
        jobClass = JobClass.Ranger,
        alignment = "Leal e bom", level = 1,
        race = Race.Human,
        attributes = Attributes(
            strength = 14,
            dexterity = 18,
            constitution = 12,
            intelligence = 12,
            wisdom = 14,
            charisma = 10
        )
    )
}

interface UserHandler {
    suspend fun retrieveChampion(userId: String): Task<DataSnapshot>
}

internal class UserAgent() : UserHandler {
    private val database = Firebase.database
    private val myRef = database.getReference("mesa").child("users")

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        myRef.child(userId).get()
}
