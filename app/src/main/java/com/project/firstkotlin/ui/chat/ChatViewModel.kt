package com.project.firstkotlin.ui.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.User
import com.project.firstkotlin.data.model.UserSingleton
import com.project.firstkotlin.data.repository.Repository

class ChatViewModel() : ViewModel() {
    private val repository = Repository.getInstance()

    val messages = MutableLiveData<MutableList<Message>>()
    val tempMessages = mutableListOf<Message>()

    val sendSuccess = MutableLiveData<Boolean>()

    init {
        repository.getMessage().addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(Message::class.java)?.let {
                    tempMessages.add(it)
                    messages.value = tempMessages
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        repository.getCurrentUser().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                UserSingleton.user = dataSnapshot.getValue(User::class.java)!!
            Log.d("user", "user"+UserSingleton.user!!.name)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun send(message: Message) {
        repository.send(message).addOnCompleteListener {
            if(!it.isSuccessful)
                sendSuccess.value = false
        }
    }
}