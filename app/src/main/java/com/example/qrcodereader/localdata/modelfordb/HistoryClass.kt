package com.example.qrcodereader.localdata.modelfordb

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "qrcode_history")
data class HistoryClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val qrContent: String,
    val time: Long): Parcelable