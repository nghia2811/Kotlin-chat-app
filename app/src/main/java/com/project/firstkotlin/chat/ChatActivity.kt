package com.project.firstkotlin.chat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.entity.Message
import com.project.firstkotlin.info.InfoActivity
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ChatActivity : AppCompatActivity() {

    private var mAuth = Firebase.auth
    private lateinit var mData: DatabaseReference

    //    private var socket = SocketSingleton.getSocket()
    var lstMessage: ArrayList<Message> = ArrayList()
    var messageAdapter: MessageAdapter? = null
    val user = mAuth.currentUser
    private var doubleClick = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

//        socket.connect()
//        socket.on("server-send-message", onListMessage)

//        var other = intent.getStringExtra("chatname")

        mData = Firebase.database.reference.child("Group").child("FirstGroup").child("message")
        mData.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                // Setting values
//                lstMessage.clear()
//                for (dataSnapshot1 in dataSnapshot.children) {
                val p: Message? = snapshot.getValue(Message::class.java)
                lstMessage.add(p!!)
                messageAdapter?.notifyItemInserted(lstMessage.size - 1)
                rv_message.smoothScrollToPosition(lstMessage.size - 1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ChatActivity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        messageAdapter = MessageAdapter(lstMessage)

        rv_message.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity, RecyclerView.VERTICAL, false)
            adapter = messageAdapter
        }

        btn_send.setOnClickListener {
            sendMessage(edt_message.text.toString())
        }

        btn_options.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

//    private val onListMessage = Emitter.Listener { args ->
//        try {
//            var message = args[0] as JSONObject
//            runOnUiThread {
//                lstMessage.add(Message(message.getString("user"), message.getString("message")))
//                messageAdapter?.notifyItemInserted(lstMessage.size - 1)
//                rv_message.smoothScrollToPosition(lstMessage.size - 1)
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(message: String) {
        if (!message.isEmpty()) {
//            val obj = JSONObject()
//            try {
//                obj.put("user", user!!.email.toString())
//                obj.put("message", message)
//                socket.emit("client-send-message", obj)
//
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val sendmessage = user?.uid?.let { Message(formatted, it, message) }
            Firebase.database.reference.child("Group").child("FirstGroup").child("message").child(
                formatted
            ).setValue(sendmessage)
            edt_message.setText(null)
        } else
            Toast.makeText(this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (doubleClick) finish()
        Toast.makeText(this, "Click 2 lần liên tiếp để thoát ứng dụng", Toast.LENGTH_SHORT).show()
        doubleClick = true
        Handler().postDelayed({ doubleClick = false }, 2000)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        socket.close()
//    }
}