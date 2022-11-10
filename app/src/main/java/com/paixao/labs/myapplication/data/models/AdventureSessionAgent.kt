package com.paixao.labs.myapplication.data.models

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.paixao.labs.myapplication.data.observeValue
import com.paixao.labs.myapplication.domain.services.AdventureSessionHandler
import com.paixao.labs.myapplication.ui.table.session.PlayerPosition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal data class Mesa(
    val user: List<Users>? = emptyList()
)

internal data class Users(
    val position: String = ""
)

internal class AdventureSessionAgent(private val firebaseDatabase: FirebaseDatabase) :
    AdventureSessionHandler {

    private val firebaseApi = firebaseDatabase.getReference(MAIN_PATH).child(PATH_SECONDARY)

    override fun loginAdventure(): Flow<PlayerPosition> =
        firebaseApi.observeValue()
            .map { dataSnapShot ->
                val teste = dataSnapShot.child("user").getValue(Users::class.java)

                val position = teste?.position?.let { PlayerPosition.valueOf(it) }
                    ?: throw IllegalArgumentException("Invalid position")

                position
            }

    override fun moveCharacter(position: PlayerPosition) {
        firebaseApi.updateChildren(mapOf("user" to Users(position = position.name)))
            .addOnSuccessListener {
            }
            .addOnFailureListener { error ->
                Log.e("Error to update data --> $error", error.toString())
            }
    }

    private companion object {
        const val MAIN_PATH = "mesa"
        const val PATH_SECONDARY = "users"
    }
}
