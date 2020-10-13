package com.project.firstkotlin.ui.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.project.firstkotlin.R
import com.project.firstkotlin.data.model.User
import com.project.firstkotlin.data.model.UserSingleton
import com.project.firstkotlin.ui.chat.ChatActivity
import com.project.firstkotlin.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    var uri: Uri? = null
    private val registerViewModel by viewModels<RegisterViewModel>()

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initEvents()
        observeData()
    }

    private fun observeData() {
        registerViewModel.registerSuccessful.observe(this) {
            if (it)
                goToMain()
            else
                Toast.makeText(this, "Registration failed!!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun initEvents() {
        btn_register.setOnClickListener {
            if (register_user.text.toString() == ""
                || register_pass.text.toString() == ""
                || register_name.text.toString() == ""
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

        iv_logo_register.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }
    }

    private fun registerAccount() {
//        register_loading.visibility = View.VISIBLE
        var email: String = register_user.text.toString()
        var password: String = register_pass.text.toString()
        var name: String = register_name.text.toString()

        var user = User("",email, name, "", arrayListOf() )
        UserSingleton.user = user
        registerViewModel.registerAccount(email,password,user,uri!!)
    }

    private fun goToMain() {
        val intent = Intent(this@RegisterActivity, ChatActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            baseContext, "Authentication successful!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile("^(.+)@(.+)$")
        val mat = pattern.matcher(email)
        return mat.matches()
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            set_avatar.setImageURI(data?.data)
            uri = data?.data
        }
    }
}