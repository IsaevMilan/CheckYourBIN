package com.example.checkyourbin.ui.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checkyourbin.R
import com.example.checkyourbin.data.db.BinHistoryEntity
import com.example.checkyourbin.databinding.ItemHistoryBinding

class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(binHistory: BinHistoryEntity) {
        binding.binText.text = binHistory.bin
        binding.schemeText.text = binHistory.scheme ?: "Unknown"
    }
}
