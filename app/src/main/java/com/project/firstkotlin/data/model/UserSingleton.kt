package com.project.firstkotlin.data.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object UserSingleton {
    var user: User? = null
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mData: DatabaseReference

    init {
        getUser()
    }

    fun getUser(){
        mAuth = Firebase.auth
        var mUser = mAuth.currentUser
        mData = Firebase.database.reference.child("User").child(mUser!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                user = dataSnapshot.getValue(User::class.java)!!
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}