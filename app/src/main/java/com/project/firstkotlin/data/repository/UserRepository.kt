package com.project.firstkotlin.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.User

class UserRepository {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mData: DatabaseReference
    var registerIsSuccess : Boolean = false
    val mutableData = MutableLiveData<MutableList<String>>()

    init {
        getUserList()
    }

    fun getUserList() {
        mData = Firebase.database.reference.child("User").child("FirstGroup").child("message")
        val lstUser = mutableListOf<String>()
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                lstUser.clear()
                dataSnapshot.children.forEach {
                    it.getValue(User::class.java)?.name?.let { it1 -> lstUser.add(it1) }
                }
                mutableData.value = lstUser
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun registerAccount(email: String, password: String, name: String, address: String): Boolean {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var mUser = mAuth.currentUser
                    var user =
                        User(mUser!!.uid, email, name, address, arrayListOf("FirstGroup"))
                    mData = Firebase.database.reference
                    mData.child("User").child(mUser.uid).setValue(user)
                    registerIsSuccess = true
                } else {
                    registerIsSuccess = false
                }
            }
        return registerIsSuccess
    }
}