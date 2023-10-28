package com.liu.bloodpressure

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.util.AppLifecycle
import com.liu.bloodpressure.util.AssetsKt
import com.liu.bloodpressure.util.logE

lateinit var application: App

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        registerActivityLifecycleCallbacks(AppLifecycle())

        val json = AssetsKt.getJson(this, "advertising.json")
        "advertising json = $json".logE()
        AdvertisingHelper.initAdvertising(json)
        MobileAds.initialize(this){}
    }
}