package com.project.firstkotlin.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : ViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String): Boolean {
        var isSuccess = true

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isSuccess = task.isSuccessful
            }

        return isSuccess
    }

    class LoginViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}