package com.jordansilva.kstore.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}