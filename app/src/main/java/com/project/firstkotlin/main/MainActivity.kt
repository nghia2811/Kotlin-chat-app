package com.project.firstkotlin.main

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.firstkotlin.R

class MainActivity : AppCompatActivity() {
    private var doubleClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}