package com.project.firstkotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.chat.ChatActivity
import com.project.firstkotlin.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Initialize Firebase Auth
            mAuth = Firebase.auth
            if (mAuth.currentUser != null) {
                val intent = Intent(this@SplashActivity, ChatActivity::class.java)
                startActivity(intent)
            } else startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}