package com.example.checkyourbin.data

import android.util.Log
import com.example.checkyourbin.data.db.BinHistoryDao
import com.example.checkyourbin.data.db.BinHistoryEntity
import com.example.checkyourbin.data.network.BinApiService

class BinRepository(
    private val apiService: BinApiService,
    private val binHistoryDao: BinHistoryDao
) {
    suspend fun getBinInfo(bin: String): Result<BinResponse> {
        return try {
            val response = apiService.getBinInfo(bin)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No data"))
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveHistory(historyEntity: BinHistoryEntity) {
        Log.d("BinRepository", "Saving history entity: $historyEntity")
        binHistoryDao.insertBinHistory(historyEntity)
    }

    suspend fun getHistory(): List<BinHistoryEntity> {
        val historyList = binHistoryDao.getBinHistory()
        Log.d("BinRepository", "Loaded history list from DB: $historyList")
        return historyList
    }

    suspend fun clearHistory() {
        Log.d("BinRepository", "Clearing all history")
        binHistoryDao.clearBinHistory()
    }
}