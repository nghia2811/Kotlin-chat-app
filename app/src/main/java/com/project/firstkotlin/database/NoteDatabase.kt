package com.project.firstkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.firstkotlin.database.dao.NoteDao
import com.project.firstkotlin.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var instance:NoteDatabase? = null

        fun getInstance(context: Context):NoteDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase").build()
            }
            return instance!!
        }
    }
}