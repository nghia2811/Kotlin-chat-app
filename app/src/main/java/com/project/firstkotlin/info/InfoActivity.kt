package com.project.firstkotlin.info

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.entity.SocketSingleton
import com.project.firstkotlin.login.MainActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_info.*
import org.json.JSONException
import org.json.JSONObject

class InfoActivity : Activity() {

    private lateinit var mAuth: FirebaseAuth
    private var socket = SocketSingleton.getSocket()
    var arrayAdapter: ArrayAdapter<String>? = null
    var userList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        socket.connect()
        socket.emit("client-request-userlist")
        socket.on("server-send-userlist", onRetrieveResult)

        mAuth = Firebase.auth
        val currentUser = mAuth.currentUser

        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userList)
        rv_user.adapter = arrayAdapter

        btn_logout.setOnClickListener {
            socket.emit("client-exits", currentUser!!.email)
            mAuth.signOut()
            finish()
            startActivity(Intent(this@InfoActivity, MainActivity::class.java))
        }
    }

    private var onRetrieveResult = Emitter.Listener { args ->
        try {
            var user = args[0] as JSONObject
            val array = user.getJSONArray("danhsach")
            userList.clear()
            runOnUiThread {
                for (i in 0..array.length() - 1) {
                    userList.add(array.getString(i))
                }
                arrayAdapter?.notifyDataSetChanged()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        socket.close()
//    }
}