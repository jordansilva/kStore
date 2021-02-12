package com.jordansilva.kstore.data.remote

import android.content.res.Resources
import com.jordansilva.kstore.R

class RawDataSource(private val resources: Resources) {
    
    fun loadData(): String {
        return resources.openRawResource(R.raw.products).bufferedReader().use { it.readText() }
    }
}