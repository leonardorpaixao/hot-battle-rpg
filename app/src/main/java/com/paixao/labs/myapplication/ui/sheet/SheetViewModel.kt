package com.paixao.labs.myapplication.ui.sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.paixao.labs.myapplication.domain.models.CharacterSheet
import com.paixao.labs.myapplication.domain.models.JobClass
import com.paixao.labs.myapplication.domain.models.Race
import com.paixao.labs.myapplication.domain.models.ScreenState
import com.paixao.labs.myapplication.domain.models.User
import com.paixao.labs.myapplication.domain.services.UserHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
internal class SheetViewModel @Inject constructor(
    private val userHandler: UserHandler
) : ViewModel() {
    // private val userHandler = UserAgent(Firebase.database)
    private val _characterSheet: MutableStateFlow<CharacterSheet> =
        MutableStateFlow(mockedHero())

    private val _screenState: MutableStateFlow<ScreenState<User>> =
        MutableStateFlow(ScreenState(isLoading = true))

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
        attributes = com.paixao.labs.myapplication.domain.models.Attributes(
            strength = 14,
            dexterity = 18,
            constitution = 12,
            intelligence = 12,
            wisdom = 14,
            charisma = 10
        )
    )
}

internal class UserAgent(database: FirebaseDatabase) : UserHandler {
    private val firebaseApi = database.getReference("mesa").child("users")

    override suspend fun retrieveChampion(userId: String): Task<DataSnapshot> =
        firebaseApi.child(userId).get()
}
