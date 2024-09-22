package com.example.checkyourbin.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkyourbin.databinding.FragmentHistoryBinding
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BinViewModel by viewModel()
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

        binding.historyRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        viewModel.history.observe(viewLifecycleOwner) { historyList ->
            historyAdapter.submitList(historyList)
        }

        viewModel.history.observe(viewLifecycleOwner) { historyList ->
            if (historyList.isEmpty()) {
                binding.historyRecycler.visibility = View.GONE
                binding.placeholderTextView.visibility = View.VISIBLE
            } else {
                binding.historyRecycler.visibility = View.VISIBLE
                binding.placeholderTextView.visibility = View.GONE
                historyAdapter.submitList(historyList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
