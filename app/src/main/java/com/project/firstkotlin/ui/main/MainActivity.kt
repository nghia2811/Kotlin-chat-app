package com.project.firstkotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.createchat.NewchatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var doubleClick = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstUser: ArrayList<String> = ArrayList()
    var mainAdapter: MainAdapter? = null
    var username: String? = null

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.MainViewModelFactory(this.application)).get(
            MainViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = Firebase.auth

        mainAdapter = MainAdapter(this, lstUser)
        rv_chat.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = mainAdapter
        }

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, NewchatActivity::class.java))
        }

//        loadDataFromFirebase()










    }

//    private fun loadDataFromFirebase() {
//        lstUser = mainViewModel.loadDatafromFirebase()
//        mainAdapter!!.notifyDataSetChanged()
//        main_loading.visibility = View.GONE
//    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}