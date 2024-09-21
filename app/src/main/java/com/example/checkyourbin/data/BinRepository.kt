package com.example.checkyourbin.data

import android.util.Log
import com.example.checkyourbin.data.network.BinApiService

class BinRepository(private val apiService: BinApiService) {

    suspend fun getBinInfo(bin: String): Result<BinResponse> {
        return try {
            val response = apiService.getBinInfo(bin)

            Log.d("BinRepository", "Request BIN: $bin")
            Log.d("BinRepository", "Response code: ${response.code()}")

            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("BinRepository", "Response body: $it")
                    Result.success(it)
                } ?: run {
                    Log.d("BinRepository", "Response body is null")
                    Result.failure(Exception("No data"))
                }
            } else {
                Log.d("BinRepository", "Error code: ${response.code()}")
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("BinRepository", "Exception: ${e.message}")
            Result.failure(e)
        }
    }
}