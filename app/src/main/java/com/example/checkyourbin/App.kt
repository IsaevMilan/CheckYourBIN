package com.example.checkyourbin

import android.app.Application
import com.example.checkyourbin.di.binModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                binModule
            )
        }
    }

    companion object {
        const val PREFERENCE_NAME = ""
    }
}