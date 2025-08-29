package com.example.nav3example

import android.app.Application
import com.example.nav3example.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Nav3ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Nav3ExampleApplication)
            modules(
                dataModule
            )
        }
    }
}