package com.example.notesapp.room_database.dao

import androidx.room.*
import com.example.notesapp.models.NotesModel
import com.example.notesapp.models.PinCodeModel

@Dao
interface PinCodeDao {
    @Query("SELECT * FROM pin_code_table ORDER BY id ASC")
    suspend fun getRealPinCode(): List<PinCodeModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPinCode(pinCode: PinCodeModel)

    @Update
    suspend fun updatePinCode(pinCode: PinCodeModel)

}