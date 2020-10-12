package com.project.firstkotlin.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProvider(this, RegisterViewModel.RegisterViewModelFactory(this.application)).get(
            RegisterViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initEvents()
    }

    private fun initEvents() {
        btn_register.setOnClickListener {
            if (register_user.text.toString() == ""
                || register_pass.text.toString() == ""
                || register_name.text.toString() == ""
                || register_address.text.toString() == ""
            ) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng thêm đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isEmailValid(register_user.text.toString())) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Vui lòng nhập đúng địa chỉ email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                registerAccount()
            }
        }

        tv_back_signup.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun registerAccount() {
        register_loading.visibility = View.VISIBLE
        var email: String = register_user.text.toString()
        var password: String = register_pass.text.toString()
        var name: String = register_name.text.toString()
        var address: String = register_address.text.toString()
//
//        if (registerViewModel.registerByEmail(email, password, name, address)) {
//            register_loading.visibility = View.INVISIBLE
//            Toast.makeText(
//                baseContext, "Successful registration!",
//                Toast.LENGTH_SHORT
//            ).show()
//            // Sign in success, update UI with the signed-in user's information
//            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
//            startActivity(intent)
//        } else {
//            // If sign in fails, display a message to the user.
//            register_loading.visibility = View.INVISIBLE
//            Toast.makeText(
//                baseContext, "Failed registration!",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

    private fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile("^(.+)@(.+)$")
        val mat = pattern.matcher(email)
        return mat.matches()
    }
}