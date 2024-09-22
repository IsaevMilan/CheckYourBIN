package com.example.checkyourbin.ui.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checkyourbin.databinding.FragmentBinCheckBinding
import com.example.checkyourbin.ui.BinRootActivity
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BinCheckFragment : Fragment() {

    private var _binding: FragmentBinCheckBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BinViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkButton.setOnClickListener {
            val bin = binding.binInput.text.toString()
            viewModel.fetchBinInfo(bin)
        }

        viewModel.binInfo.observe(viewLifecycleOwner) { binResponse ->
            // Обновление UI с результатами BIN
            binding.resultTextView.text = binResponse?.toString() ?: "No result"
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            // Обработка ошибки
            binding.resultTextView.text = errorMsg
        }

        binding.historyButton.setOnClickListener {
            (activity as? BinRootActivity)?.showHistoryFragment() // Переход к истории
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}