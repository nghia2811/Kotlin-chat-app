package com.project.firstkotlin.ui.chat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.R
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.ui.info.InfoActivity
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ChatActivity : AppCompatActivity() {

    var group: String? = null
    var username: String? = null

    private val chatViewModel: ChatViewModel by lazy {
        ViewModelProvider(this, ChatViewModel.ChatViewModelFactory(this.application)).get(
            ChatViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initControls()

        group = intent.getStringExtra("group")
        username = intent.getStringExtra("username")

        if (group != null) {

        }

        btn_send.setOnClickListener {
//            sendMessage(edt_message.text.toString())
        }

        btn_options.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initControls() {
        val adapter = MessageAdapter(this)

        rv_message.setHasFixedSize(true)
        rv_message.layoutManager = LinearLayoutManager(this)
        rv_message.adapter = adapter

        chatViewModel.fetchMessages().observe(this, Observer {
            adapter.setMessages(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(message: String) {
        if (!message.isEmpty()) {

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val sendmessage = Message(formatted, username!!, message)

            Firebase.database.reference.child("Group").child(group!!).child("message")
                .child(formatted).setValue(sendmessage)
            edt_message.setText(null)
        } else
            Toast.makeText(this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show()
    }
}