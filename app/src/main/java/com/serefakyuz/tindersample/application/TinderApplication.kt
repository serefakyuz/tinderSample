package com.serefakyuz.tindersample.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TinderApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}