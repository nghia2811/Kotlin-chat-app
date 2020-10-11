package com.project.firstkotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.main.MainActivity
import com.project.firstkotlin.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this, LoginViewModel.LoginViewModelFactory(this.application)).get(
            LoginViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initEvents()
    }

    private fun initEvents() {
        btn_login.setOnClickListener {
            if (login_user.text.toString() == "" || login_pass.text.toString() == "")
                Toast.makeText(this, "Vui lòng thêm đủ thông tin!!", Toast.LENGTH_SHORT).show()
            else {
                loginByEmail()
            }
        }

        tv_register.setOnClickListener {
            goToRegister()
        }
    }

    private fun loginByEmail() {
        login_loading.visibility = View.VISIBLE
        if (loginViewModel.loginByEmail(login_user.text.toString(), login_pass.text.toString())) {
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

    private fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            baseContext, "Authentication successful!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}