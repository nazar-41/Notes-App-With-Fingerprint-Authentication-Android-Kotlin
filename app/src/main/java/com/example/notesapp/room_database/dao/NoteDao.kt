package com.example.notesapp.room_database.dao

import androidx.room.*
import com.example.notesapp.models.NotesModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<NotesModel>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getSpecificNote(id: Int): NotesModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(note: NotesModel)

    @Delete
    suspend fun deleteNote(note: NotesModel)

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteSpecificNote(id: Int)

    @Update
    suspend fun updateNote(note: NotesModel)

}