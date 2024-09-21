package com.example.checkyourbin.data

import com.example.checkyourbin.data.db.BinHistoryDao
import com.example.checkyourbin.data.db.BinHistoryEntity
import com.example.checkyourbin.data.network.BinApiService

class BinRepository(
    private val apiService: BinApiService,
    private val binHistoryDao: BinHistoryDao
) {
    suspend fun getBinInfo(bin: String): Result<BinResponse> {

        // Добавляем сохранение истории в БД, если запрос успешен
        return try {
            val response = apiService.getBinInfo(bin)
            if (response.isSuccessful) {
                response.body()?.let {
                    binHistoryDao.insertBinHistory(
                        BinHistoryEntity(
                            bin = bin,
                            scheme = it.scheme,
                            type = it.type,
                            brand = it.brand,
                            prepaid = it.prepaid,
                            countryName = it.country?.name,
                            bankName = it.bank?.name
                        )
                    )
                    Result.success(it)
                } ?: Result.failure(Exception("No data"))
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHistory(): List<BinHistoryEntity> {
        return binHistoryDao.getBinHistory()
    }
}