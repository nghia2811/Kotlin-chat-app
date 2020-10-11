package com.project.firstkotlin.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.Message

class MessageRepository {
    private lateinit var mData: DatabaseReference
    val mutableData = MutableLiveData<MutableList<Message>>()

    init {
        getMessages()
    }

    fun getMessages() {
        mData = Firebase.database.reference.child("Group").child("FirstGroup").child("message")
        val lstMessage = mutableListOf<Message>()
        mData.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val p = snapshot.getValue(Message::class.java)
                lstMessage.add(p!!)
                mutableData.value = lstMessage
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun sendMessage(){

    }

}