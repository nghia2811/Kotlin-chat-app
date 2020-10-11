package com.project.firstkotlin.data.repository

import com.google.firebase.auth.FirebaseAuth

class LoginRepository() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var loginIsSuccess: Boolean = false

    fun loginByEmail(email: String, password: String): Boolean {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loginIsSuccess = task.isSuccessful
            }
        return loginIsSuccess
    }
}