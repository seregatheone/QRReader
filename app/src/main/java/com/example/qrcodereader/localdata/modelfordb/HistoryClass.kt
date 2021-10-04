package com.example.qrcodereader.localdata.modelfordb

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "qrcode_history")
data class HistoryClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val qrContent: String,
    val time: Long)