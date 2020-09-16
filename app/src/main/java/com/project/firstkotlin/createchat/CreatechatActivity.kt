package com.project.firstkotlin.createchat

import android.os.Bundle
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
import com.project.firstkotlin.entity.User
import kotlinx.android.synthetic.main.activity_createchat.*
import java.util.*

class CreatechatActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    var lstUser: ArrayList<String> = ArrayList()
    var mainAdapter: CreatechatAdapter? = null
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createchat)

        mAuth = Firebase.auth

        mainAdapter = CreatechatAdapter(this, lstUser)
        rv_search.apply {
            layoutManager =
                LinearLayoutManager(this@CreatechatActivity, RecyclerView.VERTICAL, false)
            adapter = mainAdapter
        }

        loadDataFromFirebase()

        edt_search.requestFocus()
        back_to_main.setOnClickListener { super.onBackPressed() }
    }


    private fun loadDataFromFirebase() {
        val user = mAuth.currentUser
        mData = Firebase.database.reference.child("User")
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                lstUser.clear()
                dataSnapshot.children.forEach {
                    it.getValue(User::class.java)?.name?.let { it1 -> lstUser.add(it1) }
                }
                mainAdapter!!.notifyDataSetChanged()
                create_loading.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                create_loading.visibility = View.GONE
                Toast.makeText(this@CreatechatActivity, "Cannot load data!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}