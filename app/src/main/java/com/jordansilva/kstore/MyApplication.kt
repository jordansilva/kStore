package com.jordansilva.kstore

import android.app.Application
import com.jordansilva.kstore.di.appModule
import com.jordansilva.kstore.di.dataModule
import com.jordansilva.kstore.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(dataModule, domainModule, appModule)
        }
    }
}