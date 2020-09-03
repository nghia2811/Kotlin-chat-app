package com.project.firstkotlin.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.chat.ChatActivity
import com.project.firstkotlin.entity.SocketSingleton
import com.project.firstkotlin.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    //private var socket: Socket? = SocketComponent.instance?.socket
    private var socket = SocketSingleton.getSocket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        socket.connect()
        // Initialize Firebase Auth
        mAuth = Firebase.auth
        if (mAuth.currentUser != null) {
            val intent = Intent(this@LoginActivity, ChatActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            if (login_user.text.toString() == "" || login_pass.text.toString() == "")
                Toast.makeText(this, "Vui lòng thêm đủ thông tin!!", Toast.LENGTH_SHORT).show()
            else {
                socket.emit("client-register-user", login_user.text.toString())
                DangNhap()
            }
        }

        tv_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun DangNhap() {
        mAuth.signInWithEmailAndPassword(login_user.text.toString(), login_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LoginActivity, ChatActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        baseContext, "Authentication successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        socket.close()
//    }
}