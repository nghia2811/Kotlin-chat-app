package com.project.firstkotlin.screens.createchat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.R
import com.project.firstkotlin.screens.chat.ChatActivity

class CreatechatAdapter(val mContext: CreatechatActivity, private val myDataset: ArrayList<String>) :
    RecyclerView.Adapter<CreatechatAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var layout: RelativeLayout
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
        holder.username.text = myDataset[position]
        holder.layout.setOnClickListener {
            var intent = Intent(mContext, ChatActivity::class.java)
            intent.putExtra("another", myDataset[position])
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount() = myDataset.size

}