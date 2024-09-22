package com.example.checkyourbin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.checkyourbin.R
import com.example.checkyourbin.ui.check.BinCheckFragment
import com.example.checkyourbin.ui.history.HistoryFragment


class BinRootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        if (savedInstanceState == null) {
            // Инициализация начального фрагмента
            supportFragmentManager.commit {
                replace(R.id.fragment_container, BinCheckFragment())  // Этот фрагмент для ввода BIN и отображения результата
            }
        }
    }

    // Функция для переключения на фрагмент истории
    fun showHistoryFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, HistoryFragment())  // Этот фрагмент для отображения истории
            addToBackStack(null)
        }
    }
}




