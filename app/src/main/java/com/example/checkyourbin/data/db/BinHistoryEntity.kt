package com.example.checkyourbin.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_history")
data class BinHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val countryName: String?,
    val bankName: String?,
    val timestamp: Long = System.currentTimeMillis()
)