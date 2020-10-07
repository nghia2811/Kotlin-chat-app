package com.project.firstkotlin.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.User
import java.util.*

class MainViewModel(application: Application) : ViewModel() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mData: DatabaseReference
    private lateinit var lstUser: ArrayList<String>

    fun loadDatafromFirebase(): ArrayList<String> {
        val user = mAuth.currentUser
        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                val p: User? = dataSnapshot.getValue(User::class.java)
                for (element in p!!.group)
                    lstUser.add(element)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        return lstUser
    }

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }

    }
}