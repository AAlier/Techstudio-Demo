package com.techstudio

import android.app.Application
import com.techstudio.di.KoinModules
import com.techstudio.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TechStudioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TechStudioApp)
            modules(KoinModules.create())
        }
    }
}