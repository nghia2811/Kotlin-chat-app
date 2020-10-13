package com.project.firstkotlin.ui.chat

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.firstkotlin.R
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.UserSingleton
import com.project.firstkotlin.ui.info.InfoActivity
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ChatActivity : AppCompatActivity() {

    var group: String? = null

    private val chatViewModel by viewModels<ChatViewModel>()

    private val adapter = MessageAdapter(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        observeData()
        initControls()
        initEvents()
    }

    private fun observeData() {
        chatViewModel.messages.observe(this) {
            adapter.setMessages(it)
            chat_loading.visibility = View.GONE
        }

        chatViewModel.sendSuccess.observe(this) {
            if (!it)
                Toast.makeText(this, "Send failed", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initEvents() {
        btn_send.setOnClickListener {
            sendMessage(edt_message.text.toString())
        }

        btn_options.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initControls() {
//        group = intent.getStringExtra("group")

        rv_message.setHasFixedSize(true)
        rv_message.layoutManager = LinearLayoutManager(this)
        rv_message.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(message: String) {
        if (message.isNotEmpty()) {

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            val sendmessage = Message(formatted, UserSingleton.user!!.name, message)

            chatViewModel.send(sendmessage)
            edt_message.text = null
        } else
            Toast.makeText(this, "Vui lòng nhập tin nhắn!", Toast.LENGTH_SHORT).show()
    }
}