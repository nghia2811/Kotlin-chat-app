package com.project.firstkotlin.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.project.firstkotlin.data.local.NoteDao
import com.project.firstkotlin.data.local.NoteDatabase
import com.project.firstkotlin.data.model.Note

class NoteRepository(app: Application) {
    private val noteDao: NoteDao

    init {
        val noteDatabase: NoteDatabase = NoteDatabase.getInstance(app)
        noteDao = noteDatabase.getNoteDao()
    }

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    fun getAllNote(): LiveData<List<Note>> = noteDao.getAllNote()
}