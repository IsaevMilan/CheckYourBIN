package com.example.checkyourbin.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.checkyourbin.R
import com.example.checkyourbin.databinding.ActivityRootBinding
import com.example.checkyourbin.ui.check.BinCheckFragment
import com.example.checkyourbin.ui.history.HistoryFragment

class BinRootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, BinCheckFragment())
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bin_check -> {
                    showBinCheckFragment()
                    true
                }

                R.id.nav_history -> {
                    showHistoryFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun showBinCheckFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, BinCheckFragment())
        }
    }

    private fun showHistoryFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, HistoryFragment())
        }
    }
}
