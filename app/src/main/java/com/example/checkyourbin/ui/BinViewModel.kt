package com.example.checkyourbin.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkyourbin.data.BinRepository
import com.example.checkyourbin.data.BinResponse
import kotlinx.coroutines.launch

class BinViewModel(private val repository: BinRepository) : ViewModel() {

    val binInfo = MutableLiveData<BinResponse?>()
    val error = MutableLiveData<String?>()

    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            val result = repository.getBinInfo(bin)
            result.onSuccess { response ->
                Log.d("BinViewModel", "BIN Info: $response")
                binInfo.value = response
            }.onFailure { exception ->
                Log.e("BinViewModel", "Error fetching BIN info: ${exception.message}")
                error.value = exception.message
            }
        }
    }
}