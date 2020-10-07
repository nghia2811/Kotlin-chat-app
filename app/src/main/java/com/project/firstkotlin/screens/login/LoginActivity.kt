package com.project.firstkotlin.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.screens.main.MainActivity
import com.project.firstkotlin.screens.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.LoginView, LoginContract.LoginPresenter, View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = Firebase.auth

        btn_login.setOnClickListener {
            if (login_user.text.toString() == "" || login_pass.text.toString() == "")
                Toast.makeText(this, "Vui lòng thêm đủ thông tin!!", Toast.LENGTH_SHORT).show()
            else {
                DangNhap()
            }
        }

        tv_register.setOnClickListener {
            goToRegister()
        }
    }

    private fun DangNhap() {
        login_loading.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(login_user.text.toString(), login_pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    login_loading.visibility = View.INVISIBLE
                    goToMain()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                    login_loading.visibility = View.INVISIBLE
                }
            }
    }

    override fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun goToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            baseContext, "Authentication successful!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onViewAttach(view: LoginContract.LoginView?) {
        TODO("Not yet implemented")
    }

    override fun onViewDetach() {
        TODO("Not yet implemented")
    }

    override fun onLoginClick() {
        TODO("Not yet implemented")
    }

    override fun onRegisterClick() {
        TODO("Not yet implemented")
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        socket.close()
//    }
}