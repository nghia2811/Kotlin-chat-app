package com.project.firstkotlin.screens.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.firstkotlin.R
import com.project.firstkotlin.model.Note
import com.project.firstkotlin.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by lazy {
        ViewModelProvider(this, NoteViewModel.NoteViewModelFactory(this.application)).get(
            NoteViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        initControls()
        initEvents()
    }

    private fun initEvents() {
        btn_addnote.setOnClickListener {
            val note = Note(add_title.text.toString(), add_description.text.toString())
            noteViewModel.insertNote(note)
        }
    }

    private fun initControls() {
        val adapter: NoteAdapter = NoteAdapter(this@NoteActivity, onItemClick)

        rv_note.setHasFixedSize(true)
        rv_note.layoutManager = LinearLayoutManager(this)
        rv_note.adapter = adapter

        noteViewModel.getAllNote().observe(this, Observer {
            adapter.setNotes(it)
        })
    }

    private val onItemClick: (Note) -> Unit = {
        val note = it
        note.title = add_title.text.toString()
        note.description = add_description.text.toString()
        noteViewModel.updateNote(note)
//        noteViewModel.deleteNote(it)
    }
}