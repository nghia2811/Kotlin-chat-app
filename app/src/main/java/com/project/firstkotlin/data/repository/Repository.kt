package com.project.firstkotlin.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.User
import com.project.firstkotlin.data.model.UserSingleton

class Repository {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()

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

    fun getUserList(): DatabaseReference =
        database.reference.child("User")
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

    fun registerAccount(email: String, password: String): Task<AuthResult> =
        auth.createUserWithEmailAndPassword(email, password)

    fun addAccountToDatabase(user: User, uri: Uri) {
        val storageRef: StorageReference =
            storage.getReferenceFromUrl("gs://moon-b54c7.appspot.com")

        val ref = storageRef.child("avatar").child(UserSingleton.user!!.email + "_avatar" + ".png")
        var uploadTask = ref.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url = task.result.toString()

                user.id = auth.currentUser!!.uid
                user.avatar = url
                database.reference.child("User").child(auth.currentUser!!.uid).setValue(user)
                UserSingleton.user = user
            } else {
                // Handle failures
                // ...
            }

        }
    }

}