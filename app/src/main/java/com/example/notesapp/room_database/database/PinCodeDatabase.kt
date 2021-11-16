package com.example.notesapp.room_database.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.models.PinCodeModel
import com.example.notesapp.room_database.dao.PinCodeDao

@Database(entities = [PinCodeModel::class], version = 1, exportSchema = false)
abstract class PinCodeDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: PinCodeDatabase? = null

        fun getDatabase(context: Context): PinCodeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PinCodeDatabase::class.java,
                    "pin_code_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun pinCodeDao(): PinCodeDao
}


//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.notesapp.models.NotesModel
//import com.example.notesapp.room_database.dao.NoteDao
//
//@Database(entities = [NotesModel::class], version = 1, exportSchema = false)
//abstract class NotesDatabase : RoomDatabase() {
//
//    companion object {
//        var notesDatabase: NotesDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): NotesDatabase {
//            if (notesDatabase == null) {
//                notesDatabase = Room.databaseBuilder(
//                    context,
//                    NotesDatabase::class.java,
//                    "notes.db"
//                )
//                    .build()
//            }
//            return notesDatabase!!
//        }
//    }
//    abstract fun noteDao(): NoteDao
//}