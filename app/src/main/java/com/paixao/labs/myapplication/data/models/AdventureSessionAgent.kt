package com.paixao.labs.myapplication.data.models

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.paixao.labs.myapplication.domain.services.AdventureSessionHandler
import com.paixao.labs.myapplication.ui.table.session.PlayerPosition
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
        firebaseApi.observeValue2()
            .map { dataSnapShot ->
                val teste = dataSnapShot.child("user").getValue(Users::class.java)


                val position = teste?.position?.let { PlayerPosition.valueOf(it) }
                    ?: throw IllegalArgumentException("Invalid position")
                position
            }


    private companion object {
        const val MAIN_PATH = "mesa"
        const val PATH_SECONDARY = "users"
    }

    override fun moveCharacter(position: PlayerPosition) {
        firebaseApi.updateChildren(mapOf("user" to Users(position = position.name)))
            .addOnSuccessListener {
            }
            .addOnFailureListener { error ->
                Log.e("Erro to update data --> $error", error.toString())
                Log.e("Erro to update data --> $error", error.toString())
            }
    }
}

fun <T> DatabaseReference.observeValue(): Flow<T?> =
    callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot as? T)
            }
        }


        addValueEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }

fun DatabaseReference.observeValue2(): Flow<DataSnapshot> =
    callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot)
            }
        }


        addValueEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }


fun DatabaseReference.observeChildEvent(): Flow<DataSnapshot?> {
    return callbackFlow {
        val listener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Child moved for database reference: ${snapshot.ref}", "")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                trySend(snapshot)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                trySend(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                trySend(snapshot)
            }
        }

        addChildEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }
}