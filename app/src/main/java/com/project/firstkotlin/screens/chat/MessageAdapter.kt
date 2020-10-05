package com.project.firstkotlin.screens.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.R
import com.project.firstkotlin.model.Message

class MessageAdapter(val mContext: ChatActivity, private val myDataset: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var message: TextView

        init {
            username = itemView.findViewById(R.id.tv_username)
            message = itemView.findViewById(R.id.tv_message)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var message: TextView

        init {
            username = itemView.findViewById(R.id.tv_myusername)
            message = itemView.findViewById(R.id.tv_mymessage)
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
    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) === 0) {
            (holder as FriendViewHolder).username.text = myDataset[position].sender
            (holder as FriendViewHolder).message.text = myDataset[position].message
        } else {
            (holder as MyViewHolder).username.text = myDataset[position].sender
            (holder as MyViewHolder).message.text = myDataset[position].message
        }
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.username.text = myDataset[position].sender
//        holder.message.text = myDataset[position].message
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    override fun getItemViewType(position: Int): Int {
        if (myDataset[position].sender == mContext.username) return 1
        else return 0
    }

}