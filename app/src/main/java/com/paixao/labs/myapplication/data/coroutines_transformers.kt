package com.paixao.labs.myapplication.data

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
fun DatabaseReference.observeValue(): Flow<DataSnapshot> =
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
