package com.project.firstkotlin.screens.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.screens.createchat.CreatechatActivity
import com.project.firstkotlin.model.User
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var doubleClick = false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstUser: ArrayList<String> = ArrayList()
    var mainAdapter: MainAdapter? = null
    var username: String? = null

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
            startActivity(Intent(this@MainActivity, CreatechatActivity::class.java))
        }

        loadDataFromFirebase()
    }

    private fun loadDataFromFirebase(){
        val user = mAuth.currentUser
        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                lstUser.clear()
                val p: User? = dataSnapshot.getValue(User::class.java)
                username = p!!.name
                for (element in p.group)
                    lstUser.add(element)
                mainAdapter!!.notifyDataSetChanged()
                main_loading.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                main_loading.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }
}