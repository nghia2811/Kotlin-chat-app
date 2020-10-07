package com.project.firstkotlin.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.User

class UserRepository {
    private lateinit var mData: DatabaseReference
    private lateinit var lstUser: MutableList<String>

    fun getUserList(): LiveData<MutableList<String>> {
        val mutableData = MutableLiveData<MutableList<String>>()

        mData = Firebase.database.reference.child("User")
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                lstUser.clear()
                dataSnapshot.children.forEach {
                    it.getValue(User::class.java)?.name?.let { it1 -> lstUser.add(it1) }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        mutableData.value = lstUser
        return mutableData
    }
}