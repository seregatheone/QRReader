package com.example.qrcodereader.localdata

import androidx.lifecycle.LiveData
import com.example.qrcodereader.localdata.database.HistoryDao
import com.example.qrcodereader.localdata.modelfordb.HistoryClass

class HistoryRepository (private val historyDao : HistoryDao){
    val readAllData: LiveData<List<HistoryClass>> = historyDao.readAllData()

    suspend fun addNewHistory(history: HistoryClass){
        historyDao.addHistory(history)
    }

    suspend fun deleteAll(){
        historyDao.deleteAll()
    }
}