package com.example.peopletask

import android.app.Application
import android.content.Context
import com.downloader.PRDownloader
import timber.log.Timber

class PersonsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        PRDownloader.initialize(applicationContext)
    }

}