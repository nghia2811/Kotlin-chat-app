package com.project.firstkotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.Message

class MessageRepository {
    //    private var mAuth = Firebase.auth
    private lateinit var mData: DatabaseReference

    fun getMessages(): LiveData<MutableList<Message>> {
        val mutableData = MutableLiveData<MutableList<Message>>()

        mData = Firebase.database.reference.child("Group").child("FirstGroup").child("message")
        val lstMessage = mutableListOf<Message>()
        mData.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val p = snapshot.getValue(Message::class.java)
                lstMessage.add(p!!)
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

        mutableData.value = lstMessage
        return mutableData
    }

}