package com.example.peopletask

import android.app.Application
import timber.log.Timber

class PersonsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}