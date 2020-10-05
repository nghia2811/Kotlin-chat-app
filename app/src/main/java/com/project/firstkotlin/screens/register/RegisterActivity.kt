package com.project.firstkotlin.screens.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.model.SocketSingleton
import com.project.firstkotlin.model.User
import com.project.firstkotlin.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private var socket = SocketSingleton.getSocket()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            if (register_user.text.toString() == ""
                || register_pass.text.toString() == ""
                || register_name.text.toString() == ""
                || register_address.text.toString() == "") {
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
                DangKi()
            }
        }

        tv_back_signup.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun DangKi() {
        register_loading.visibility = View.VISIBLE
        var email: String = register_user.text.toString()
        var password: String= register_pass.text.toString()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var mUser = mAuth.currentUser
                    var user = User(mUser!!.uid, email, register_name.text.toString(), register_address.text.toString(), arrayListOf("FirstGroup"))
                    mData = Firebase.database.reference
                    mData.child("User").child(mUser!!.uid).setValue(user)
                    socket.emit("client-register-user", email)
                    register_loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        baseContext, "Successful registration!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    register_loading.visibility = View.INVISIBLE
                    Toast.makeText(
                        baseContext, "Failed registration!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile("^(.+)@(.+)$")
        val mat = pattern.matcher(email)
        return mat.matches()
    }
}