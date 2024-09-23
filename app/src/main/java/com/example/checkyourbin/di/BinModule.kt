package com.example.checkyourbin.di

import androidx.room.Room
import com.example.checkyourbin.data.BinRepository
import com.example.checkyourbin.data.db.BinDatabase
import com.example.checkyourbin.data.network.RetrofitClient
import com.example.checkyourbin.ui.BinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val binModule = module {
    single { RetrofitClient.create() }
    single { BinRepository(get(), get()) }
    single {
        Room.databaseBuilder(get(), BinDatabase::class.java, "bin_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<BinDatabase>().binHistoryDao() }
    viewModel { BinViewModel(get()) }
}