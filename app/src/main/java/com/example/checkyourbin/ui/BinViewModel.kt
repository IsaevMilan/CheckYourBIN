package com.example.checkyourbin.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkyourbin.data.BinRepository
import com.example.checkyourbin.data.BinResponse
import com.example.checkyourbin.data.db.BinHistoryEntity
import kotlinx.coroutines.launch


class BinViewModel(private val repository: BinRepository) : ViewModel() {

    val binInfo = MutableLiveData<BinResponse?>()
    val error = MutableLiveData<String?>()
    val history = MutableLiveData<List<BinHistoryEntity>>()
    val binInputText = MutableLiveData<String>()

    fun fetchBinInfo(bin: String) {
        Log.d("BinViewModel", "Fetching BIN info for: $bin")
        viewModelScope.launch {
            val result = repository.getBinInfo(bin)
            result.onSuccess { response ->
                binInfo.value = response
                Log.d("BinViewModel", "BIN info fetched successfully: $response")
                saveBinHistory(response)
            }.onFailure { exception ->
                error.value = exception.message
                Log.e("BinViewModel", "Error fetching BIN info: ${exception.message}")
            }
        }
    }

    private fun saveBinHistory(binResponse: BinResponse) {
        viewModelScope.launch {
            Log.d("BinViewModel", "Saving BIN history: $binResponse")
            val bin = binInputText.value ?: "Unknown"

            val historyEntity = BinHistoryEntity(
                bin = bin,
                scheme = binResponse.scheme ?: "Unknown",
                type = binResponse.type ?: "Unknown",
                countryName = binResponse.country?.name ?: "Unknown",
                latitude = binResponse.country?.latitude?.toString() ?: "Unknown",
                longitude = binResponse.country?.longitude?.toString() ?: "Unknown",
                bankName = binResponse.bank?.name ?: "Unknown",
                bankPhone = binResponse.bank?.phone ?: "Unknown",
                bankUrl = binResponse.bank?.url ?: "Unknown",
                bankCity = binResponse.bank?.city ?: "Unknown"

            )
            Log.d("BinViewModel", "Saving history entity: $historyEntity")
            repository.saveHistory(historyEntity)
            Log.d("BinViewModel", "BIN history saved successfully")
            loadHistory()
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            val historyList = repository.getHistory()
            Log.d("BinViewModel", "Loaded history list: $historyList")
            history.value = historyList
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            history.value = emptyList()
        }
    }

}
