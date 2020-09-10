package com.project.firstkotlin.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.entity.Message
import com.project.firstkotlin.R
import com.project.firstkotlin.chat.ChatActivity
import com.project.firstkotlin.entity.Group
import com.project.firstkotlin.entity.User
import com.project.firstkotlin.register.RegisterActivity

class MainAdapter(val context: Context, private val myDataset: ArrayList<User>) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var layout: LinearLayout
//        var message: TextView

        init {
            username = itemView.findViewById(R.id.tv_chatname)
            layout = itemView.findViewById(R.id.layout_chat)
//            message = itemView.findViewById(R.id.tv_message)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text = myDataset[position].name
        holder.layout.setOnClickListener {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("chatname", myDataset[position].email)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = myDataset.size
}