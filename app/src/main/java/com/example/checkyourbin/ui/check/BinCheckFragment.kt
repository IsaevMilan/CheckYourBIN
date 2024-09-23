package com.example.checkyourbin.ui.check

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.checkyourbin.databinding.FragmentBinCheckBinding
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class BinCheckFragment : Fragment() {

    private var _binding: FragmentBinCheckBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BinViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.binInputText.observe(viewLifecycleOwner) { binText ->
            binText?.let {
                if (binding.tvBinInput.text.toString().replace(" ", "") != it) {
                    binding.tvBinInput.setText(it.chunked(4).joinToString(" "))
                }
            }
        }

        binding.checkButton.setOnClickListener {
            val bin = binding.tvBinInput.text.toString()
            viewModel.fetchBinInfo(bin)
        }

        binding.tvBinInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                binding.checkButton.performClick()

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.tvBinInput.windowToken, 0)

                true
            } else {
                false
            }
        }

        binding.tvBinInput.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return

                val input = s.toString().replace(" ", "")

                if (input.length > 4) {
                    isFormatting = true

                    val formatted = input.chunked(4).joinToString(" ")

                    if (s.toString() != formatted) {
                        binding.tvBinInput.setText(formatted)
                        binding.tvBinInput.setSelection(formatted.length)
                    }

                    viewModel.binInputText.value = input
                    isFormatting = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.checkButton.setOnClickListener {
            val bin =
                binding.tvBinInput.text.toString().replace(" ", "")
            viewModel.fetchBinInfo(bin)
        }

        viewModel.binInfo.observe(viewLifecycleOwner) { binResponse ->
            binResponse?.let {
                binding.tvCountry.text = "Country: ${binResponse.country?.name ?: "N/A"}"
                binding.tvCoordinates.text =
                    "Coordinates: ${binResponse.country?.latitude ?: "N/A"}," +
                            " " + "${binResponse.country?.longitude ?: "N/A"}"
                binding.tvScheme.text = "Card Scheme: ${binResponse.scheme ?: "N/A"}"
                binding.tvCardType.text = "Card Type: ${binResponse.type ?: "N/A"}"
                binding.tvBankDetails.text = "Bank: ${binResponse.bank?.name ?: "N/A"}"
                binding.tvPhone.text = binResponse.bank?.phone ?: "N/A"
                binding.tvUrl.text = binResponse.bank?.url ?: "N/A"
                binding.tvBankCity.text = binResponse.bank?.city ?: "N/A"

            }
        }

        binding.tvCoordinates.setOnClickListener {
            val uri = Uri.parse("geo:40.7128,-74.0060")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        binding.tvCoordinates.setTextColor(Color.BLUE)

        //Данные не загружаются
        /*binding.tvPhone.setTextColor(Color.BLUE)
        binding.tvUrl.setTextColor(Color.BLUE)*/

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg != null) {
                binding.tvError.visibility = View.VISIBLE
                binding.resultSection.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.GONE
                binding.resultSection.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
