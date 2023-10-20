package com.liu.bloodpressure

import android.app.Application
import com.liu.bloodpressure.util.AppLifecycle

lateinit var application: App

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        registerActivityLifecycleCallbacks(AppLifecycle())
    }
}