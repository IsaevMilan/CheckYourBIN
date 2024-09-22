package com.example.checkyourbin.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BinHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinHistory(binHistory: BinHistoryEntity)

    @Query("SELECT * FROM bin_history ORDER BY timestamp DESC")
    suspend fun getBinHistory(): List<BinHistoryEntity>
}