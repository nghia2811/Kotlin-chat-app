package com.project.firstkotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
class Note(var title: String = "", var description : String = ""): Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

}