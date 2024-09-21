package com.example.checkyourbin.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.checkyourbin.R
import com.example.checkyourbin.data.BinRepository
import com.example.checkyourbin.data.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BinCheckActivity : AppCompatActivity() {
    private lateinit var repository: BinRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = RetrofitClient.create()
        repository = BinRepository(apiService)


        testApiCall("45717360")
    }

    // Функция для тестирования API вызова
    private fun testApiCall(bin: String) {
        // Используем корутины для асинхронного запроса
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val result = repository.getBinInfo(bin)

                result.onSuccess { response ->
                    Log.d("BinLookupActivity", "Success: $response")
                }.onFailure { error ->
                    Log.e("BinLookupActivity", "Error: ${error.message}")
                }
            } catch (e: Exception) {
                Log.e("BinLookupActivity", "Exception: ${e.message}")
            }
        }
    }
}




