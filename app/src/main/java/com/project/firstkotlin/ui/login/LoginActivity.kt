package com.project.firstkotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.chat.ChatActivity
import com.project.firstkotlin.ui.main.MainActivity
import com.project.firstkotlin.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initEvents()
        observeData()
    }

    private fun observeData() {
        loginViewModel.isLogin.observe(this) {
            if(it)
                goToMain()
            else
                Toast.makeText(this, "Authentication failed!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initEvents() {
        btn_login.setOnClickListener {
            if (login_user.text.toString().isEmpty() || login_pass.text.toString().isEmpty())
                Toast.makeText(this, "Vui lòng thêm đủ thông tin!!", Toast.LENGTH_SHORT).show()
            else {
                loginViewModel.login(login_user.text.toString(),login_pass.text.toString())
            }
        }

        tv_register.setOnClickListener {
            goToRegister()
        }
    }

    private fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToMain() {
        val intent = Intent(this@LoginActivity, ChatActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            baseContext, "Authentication successful!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}