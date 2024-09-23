package com.example.checkyourbin.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val scheme: String?,
    val type: String?,
    val countryName: String?,
    val latitude: String?,
    val longitude: String?,
    val bankName: String?,
    val bankPhone: String?,
    val bankUrl: String?,
    val timestamp: Long = System.currentTimeMillis(),
    val bankCity: String?
)