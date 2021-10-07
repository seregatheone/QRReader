package com.example.qrcodereader.fragments.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.qrcodereader.localdata.HistoryRepository
import com.example.qrcodereader.localdata.database.HistoryDatabase
import com.example.qrcodereader.localdata.modelfordb.HistoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application){
    val readAllData: LiveData<List<HistoryClass>>
    private val repository: HistoryRepository

    init {
        val historyDao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao)
        readAllData = repository.readAllData
    }
    fun addHistory(history: HistoryClass){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNewHistory(history)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
}