package com.example.checkyourbin.ui.history

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.checkyourbin.data.db.BinHistoryEntity
import com.example.checkyourbin.databinding.ItemHistoryBinding

class HistoryViewHolder(private val binding: ItemHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(binHistory: BinHistoryEntity) {
        with(binding) {
            binText.text = "BIN: ${binHistory.bin}"
            tvCountry.text = "Country: ${binHistory.countryName ?: "Unknown"}"
            tvCoordinates.text =
                "Coordinates: ${binHistory.latitude}, ${binHistory.longitude}"
            tvScheme.text = "Card Scheme: ${binHistory.scheme ?: "Unknown"}"
            tvCardType.text = "Card Type: ${binHistory.type ?: "Unknown"}"
            tvBankDetails.text = "Bank Details: ${binHistory.bankName ?: "Unknown"}"
            tvPhone.text = "Phone: ${binHistory.bankPhone ?: "Unknown"}"
            tvUrl.text = "Url: ${binHistory.bankUrl ?: "Unknown"}"
            bankCity.text = "City: ${binHistory.bankCity ?: "Unknown"}"

            tvCoordinates.setTextColor(Color.BLUE)



            tvCoordinates.setOnClickListener {
                val uri = Uri.parse("geo:${binHistory.latitude},${binHistory.longitude}")
                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage("com.google.android.apps.maps")
                }
                binding.root.context.startActivity(intent)
            }

            /*tvPhone.setTextColor(Color.BLUE)
           tvUrl.setTextColor(Color.BLUE)*/

           /* tvPhone.setOnClickListener {
                val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${binHistory.bankPhone}")
                }
                binding.root.context.startActivity(phoneIntent)
            }

            tvUrl.setOnClickListener {
                val urlIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(binHistory.bankUrl)
                }
                binding.root.context.startActivity(urlIntent)
            }*/
        }
    }
}
