package com.nikhil.androidify

import android.app.Application
import timber.log.Timber

class AndroidifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}