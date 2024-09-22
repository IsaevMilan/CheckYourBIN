package com.example.checkyourbin.data

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

    // Сохранение истории запроса в базу данных
    suspend fun saveHistory(history: BinHistoryEntity) {
        binHistoryDao.insertBinHistory(history)
    }

    // Получение всей истории из базы данных
    suspend fun getHistory(): List<BinHistoryEntity> {
        return binHistoryDao.getBinHistory()
    }
}