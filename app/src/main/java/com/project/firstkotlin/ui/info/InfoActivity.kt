package com.project.firstkotlin.ui.info

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : Activity() {

    private lateinit var mAuth: FirebaseAuth
//    private var socket = SocketSingleton.getSocket()
    var arrayAdapter: ArrayAdapter<String>? = null
    var userList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

//        socket.connect()
//        socket.emit("client-request-userlist")
//        socket.on("server-send-userlist", onRetrieveResult)

//        info_loading.visibility = View.VISIBLE

        mAuth = Firebase.auth
        val currentUser = mAuth.currentUser

        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userList)
        rv_user.adapter = arrayAdapter

        btn_logout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}