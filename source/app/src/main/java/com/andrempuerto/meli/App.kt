package com.andrempuerto.meli

import android.app.Application
import com.andrempuerto.meli.utils.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        //Init Timber
        Logger.init()
    }
}