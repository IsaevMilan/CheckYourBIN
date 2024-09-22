package com.example.checkyourbin.ui

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

    // Метод для получения информации о BIN
    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            val result = repository.getBinInfo(bin)
            result.onSuccess { response ->
                binInfo.value = response

                // Сохранить успешный запрос в историю
                saveBinHistory(response)
            }.onFailure { exception ->
                error.value = exception.message
            }
        }
    }

    // Сохранение истории в базу данных
    private fun saveBinHistory(binResponse: BinResponse) {
        viewModelScope.launch {
            val historyEntity = BinHistoryEntity(
                bin = binResponse.number.length.toString(),
                scheme = binResponse.scheme,
                type = binResponse.type,
                brand = binResponse.brand,
                prepaid = binResponse.prepaid,
                countryName = binResponse.country?.name,
                bankName = binResponse.bank?.name
            )
            repository.saveHistory(historyEntity)
        }
    }

    // Получение истории из базы данных
    fun loadHistory() {
        viewModelScope.launch {
            val historyList = repository.getHistory()
            history.value = historyList
        }
    }
}