package com.example.notesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pin_code_table")
class PinCodeModel :Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Int ?= null

    @ColumnInfo(name = "pin_code")
    var pinCode: Int ?= null

    override fun toString(): String {
        return "$pinCode"
    }
}