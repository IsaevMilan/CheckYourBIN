package com.example.checkyourbin.ui.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.checkyourbin.databinding.FragmentBinCheckBinding
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.text.util.Linkify

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

        // Set up the button click listener to fetch BIN info
        binding.checkButton.setOnClickListener {
            val bin = binding.binInput.text.toString()
            viewModel.fetchBinInfo(bin)
        }

        // Observe BIN info data
        viewModel.binInfo.observe(viewLifecycleOwner) { binResponse ->
            if (binResponse != null) {
                // Update UI with the BIN info data
                binding.countryTextView.text = "Country: ${binResponse.country?.name ?: "N/A"}"
                binding.coordinatesTextView.text = "Coordinates: ${binResponse.country?.latitude ?: "N/A"}, ${binResponse.country?.longitude ?: "N/A"}"
                binding.cardTypeTextView.text = "Card Type: ${binResponse.type ?: "N/A"}"
                binding.bankDetailsTextView.text = "Bank: ${binResponse.bank?.name ?: "N/A"}"
                binding.phoneTextView.text = binResponse.bank?.phone ?: "N/A"
                binding.emailTextView.text = binResponse.bank?.url ?: "N/A"

                // Make phone and email clickable
                Linkify.addLinks(binding.phoneTextView, Linkify.PHONE_NUMBERS)
                Linkify.addLinks(binding.emailTextView, Linkify.EMAIL_ADDRESSES)
            }
        }

        // Observe error messages and show placeholder in case of errors
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg != null) {
                binding.errorTextView.text = "It seems an error occurred, please try again."
                binding.errorTextView.visibility = View.VISIBLE
                // Hide the result section on error
                binding.resultSection.visibility = View.GONE
            } else {
                // Hide error message and show the result section
                binding.errorTextView.visibility = View.GONE
                binding.resultSection.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
