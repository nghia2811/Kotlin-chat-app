package com.project.firstkotlin.screens.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.firstkotlin.R
import com.project.firstkotlin.model.Note

class NoteAdapter(
    private val context: Context,
    private val onClick: (Note) -> Unit
//    private val onDelete: (Note) -> Unit,
    ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var notes: List<Note> = listOf()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById(R.id.tv_username)
        private val txtDescrition: TextView  = itemView.findViewById(R.id.tv_message)
        private val layout: LinearLayout  = itemView.findViewById(R.id.layout_message)

        fun onBind(note: Note) {
            txtTitle.text = note.title
            txtDescrition.text = note.description
            layout.setOnClickListener { onClick(note) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(notes[position])
    }

    override fun getItemCount(): Int =notes.size

    fun setNotes(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}