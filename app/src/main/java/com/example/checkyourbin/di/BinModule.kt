package com.example.checkyourbin.di

import com.example.checkyourbin.data.BinRepository
import com.example.checkyourbin.data.network.RetrofitClient
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val binModule = module {
    single { RetrofitClient.create() }
    single { BinRepository(get()) }
    viewModel { BinViewModel(get()) }
}