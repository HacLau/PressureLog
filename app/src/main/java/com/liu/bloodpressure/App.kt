package com.liu.bloodpressure

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
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
        FirebaseApp.initializeApp(this)
        if (AdvertisingHelper.launchAd.cache.isNotEmpty().not()) {
            "Advertising launchAd cacheIsEmpty".logE()
            AdvertisingHelper.launchAd.load(this)
        }
        if (AdvertisingHelper.recordAd.cache.isNotEmpty().not()) {
            "Advertising recordAd cacheIsEmpty".logE()
            AdvertisingHelper.recordAd.load(this)
        }
        if (AdvertisingHelper.historyAd.cache.isNotEmpty().not()) {
            "Advertising historyAd cacheIsEmpty".logE()
            AdvertisingHelper.historyAd.load(this)
        }
        if (AdvertisingHelper.alarmAd.cache.isNotEmpty().not()) {
            "Advertising alarmAd cacheIsEmpty".logE()
            AdvertisingHelper.alarmAd.load(this)
        }
    }
}