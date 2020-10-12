package com.project.firstkotlin.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.User

class Repository {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    companion object {
        private var INSTANCE: Repository? = null

        fun getInstance(): Repository = INSTANCE ?: Repository().apply {
            INSTANCE = this
        }
    }

    fun login(email: String, password: String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    fun send(message: Message): Task<Void> =
        database.reference.child("Group").child("FirstGroup").child("message")
            .child(message.date)
            .setValue(message)

    fun getMessage(): DatabaseReference =
        database.reference.child("Group").child("FirstGroup").child("message")

    fun getCurrentUser(): DatabaseReference =
        database.reference.child("User").child(auth.currentUser!!.uid)

    fun getUserList() : DatabaseReference =
        Firebase.database.reference.child("User")
//        mData.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Setting values
//                lstUser.clear()
//                dataSnapshot.children.forEach {
//                    it.getValue(User::class.java)?.name?.let { it1 -> lstUser.add(it1) }
//                }
//                mutableData.value = lstUser
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        })

    fun registerAccount(user: User, password: String): Task<AuthResult> =
        auth.createUserWithEmailAndPassword(user.email, password)
}