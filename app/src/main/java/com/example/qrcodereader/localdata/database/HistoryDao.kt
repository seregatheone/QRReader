package com.example.qrcodereader.localdata.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.qrcodereader.localdata.modelfordb.HistoryClass

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history : HistoryClass)

    @Query("SELECT * FROM qrcode_history ORDER by id ASC")
    fun readAllHistory(): LiveData<List<HistoryClass>>

    @Update
    suspend fun updateHistory(history : HistoryClass)

    @Delete
    suspend fun deleteHistory(history : HistoryClass)

    @Query("DELETE FROM qrcode_history")
    suspend fun deleteAll()
}