package com.example.qrcodereader.localdata.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qrcodereader.localdata.modelfordb.HistoryClass

@Database(entities = [HistoryClass::class],version = 1,exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao() : HistoryDao

    companion object{
        @Volatile
        private var INSTANCE : HistoryDatabase? = null

        fun getDatabase(context: Context) : HistoryDatabase{
            val tempInstance = INSTANCE
            if (tempInstance !=null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}