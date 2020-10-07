package com.project.firstkotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.firstkotlin.data.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note : Note)

    @Update
    suspend fun  updateNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("select * from note_table")
    fun getAllNote(): LiveData<List<Note>>

    @Query("select * from note_table where title =:title")
    fun getNoteByTitle(title: String): LiveData<List<Note>>
}