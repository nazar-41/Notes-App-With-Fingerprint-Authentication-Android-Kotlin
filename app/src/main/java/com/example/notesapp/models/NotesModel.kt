package com.example.notesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
class NotesModel :Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Int ?= null

    @ColumnInfo(name = "title")
    var title: String ?= null

    @ColumnInfo(name = "date_created")
    var date_created: String ?= null

    @ColumnInfo(name = "time_created")
    var time_created: String ?= null

    @ColumnInfo(name = "note_text")
    var note_text: String ?= null

    @ColumnInfo(name = "image_path")
    var imagePath: String ?= null

    @ColumnInfo(name = "web_link")
    var webLink: String ?= null

    @ColumnInfo(name = "color")
    var color: String ?= null

    override fun toString(): String {
        return "$title : $date_created"
    }
}