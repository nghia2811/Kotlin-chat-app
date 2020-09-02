package com.project.firstkotlin.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.`object`.Message
import com.project.firstkotlin.`object`.SocketSingleton
import com.project.firstkotlin.info.InfoActivity
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONException
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var socket = SocketSingleton.getSocket()
    var lstMessage: ArrayList<Message> = ArrayList()
    var messageAdapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        socket.connect()
        socket.on("server-send-message", onListMessage)

        mAuth = Firebase.auth
        val user = mAuth.currentUser

        messageAdapter = MessageAdapter(lstMessage)

        rv_message.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL, false)
            // set the custom adapter to the RecyclerView
            adapter = messageAdapter
        }

        chatHeading.text = "Chat group - (${user!!.email})"

        btn_send.setOnClickListener {
            if (!edt_message.text.toString().isEmpty()) {
                val obj = JSONObject()
                try {
                    obj.put("user", user.email.toString())
                    obj.put("message", edt_message.text.toString())
                    socket.emit("client-send-message", obj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                edt_message.setText(null)
            } else
                Toast.makeText(this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show()
        }

        btn_options.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private val onListMessage = Emitter.Listener { args ->
        try {
            var message = args[0] as JSONObject
            runOnUiThread {
                lstMessage.add(Message(message.getString("user"), message.getString("message")))
                messageAdapter?.notifyItemInserted(lstMessage.size - 1)
                rv_message.smoothScrollToPosition(lstMessage.size - 1)
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