package com.example.checkyourbin.ui.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.checkyourbin.data.db.BinHistoryEntity
import com.example.checkyourbin.databinding.ItemHistoryBinding

class BinHistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    private var historyList: List<BinHistoryEntity> = emptyList()

    fun submitList(list: List<BinHistoryEntity>) {
        historyList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val binHistory = historyList[position]
        Log.d("Adapter", "Binding item: $binHistory at position $position")
        holder.bind(binHistory)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }


}