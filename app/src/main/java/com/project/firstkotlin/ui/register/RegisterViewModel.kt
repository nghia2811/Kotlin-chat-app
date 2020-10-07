package com.project.firstkotlin.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.User

class RegisterViewModel(application: Application) : ViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mData: DatabaseReference

    fun registerAccount(email: String, password: String, name: String, address: String): Boolean {
        var isSuccess = false

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var mUser = mAuth.currentUser
                    var user = User(mUser!!.uid, email, name, address, arrayListOf("FirstGroup"))
                    mData = Firebase.database.reference
                    mData.child("User").child(mUser.uid).setValue(user)
                    isSuccess = true
                } else {
                    isSuccess = false
                }
            }

        return isSuccess
    }

    class RegisterViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}