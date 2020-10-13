package com.project.firstkotlin.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.R
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.UserSingleton

class MessageAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var messages = mutableListOf<Message>()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tusername: TextView = itemView.findViewById(R.id.tv_username)
        private var tmessage: TextView = itemView.findViewById(R.id.tv_message)

        fun onBind(message: Message) {
            tusername.text = message.sender
            tmessage.text = message.message
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tusername: TextView = itemView.findViewById(R.id.tv_myusername)
        private var tmessage: TextView = itemView.findViewById(R.id.tv_mymessage)

        fun onBind(message: Message) {
            tusername.text = message.sender
            tmessage.text = message.message
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view
        val view: View
        return if (viewType === 0) { // for call layout
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            FriendViewHolder(view)
        } else { // for email layout
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_mymessage,
                parent,
                false
            )
            MyViewHolder(view)
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) === 0) {
            (holder as FriendViewHolder).onBind(messages[position])
        } else {
            (holder as MyViewHolder).onBind(messages[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        if (messages[position].sender == UserSingleton.user?.name) return 1
        return 0
    }

    fun setMessages(messages: MutableList<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}