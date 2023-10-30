package com.liu.bloodpressure

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.net.RemoteConfigHelper
import com.liu.bloodpressure.util.AppLifecycle
import com.liu.bloodpressure.util.AssetsKt
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.no

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
        AdvertisingHelper.launchAd.cache.isNotEmpty().no {
            "Advertising launchAd cacheIsEmpty".logE()
            AdvertisingHelper.launchAd.load(this)
        }
        AdvertisingHelper.recordAd.cache.isNotEmpty().no {
            "Advertising recordAd cacheIsEmpty".logE()
            AdvertisingHelper.recordAd.load(this)
        }
        AdvertisingHelper.historyAd.cache.isNotEmpty().no {
            "Advertising historyAd cacheIsEmpty".logE()
            AdvertisingHelper.historyAd.load(this)
        }
        AdvertisingHelper.alarmAd.cache.isNotEmpty().no {
            "Advertising alarmAd cacheIsEmpty".logE()
            AdvertisingHelper.alarmAd.load(this)
        }
        BuildConfig.DEBUG.no{
            RemoteConfigHelper.getRemote()
        }


    }
}