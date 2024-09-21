package com.example.checkyourbin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.checkyourbin.R

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: BinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


    }
}
