package com.example.checkyourbin.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkyourbin.databinding.FragmentHistoryBinding
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BinViewModel by activityViewModel()
    private lateinit var historyAdapter: BinHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = BinHistoryAdapter()
        historyAdapter.notifyDataSetChanged()

        binding.historyRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        viewModel.history.observe(viewLifecycleOwner) { historyList ->
            if (historyList.isNotEmpty()) {
                historyAdapter.submitList(historyList)
                Log.d("HistoryFragment", "History list set in adapter: ${historyList.size} items")
            } else {
                Log.d("HistoryFragment", "History list is empty")
            }
        }


        viewModel.loadHistory()

        viewModel.history.observe(viewLifecycleOwner) { historyList ->
            Log.d("HistoryFragment", "History data observed: $historyList")
            if (historyList.isEmpty()) {
                binding.historyRecycler.visibility = View.GONE
                binding.tvPlaceholder.visibility = View.VISIBLE
            } else {
                binding.historyRecycler.visibility = View.VISIBLE
                binding.tvPlaceholder.visibility = View.GONE
                historyAdapter.submitList(historyList)
                Log.d("HistoryFragment", "History list set in adapter")
            }
        }

        binding.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
