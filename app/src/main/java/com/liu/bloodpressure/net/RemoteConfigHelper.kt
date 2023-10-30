package com.liu.bloodpressure.net

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.liu.bloodpressure.BuildConfig
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.util.no
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes


object RemoteConfigHelper {
    private val remoteConfig by lazy {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })
        }
    }

    fun getRemote() {
        getConfig()
        remoteConfig.fetchAndActivate().addOnSuccessListener {
            getConfig()
        }
    }

    private fun getConfig() {
        getAdvertisingConfig()
    }

    private fun getAdvertisingConfig() {
        kotlin.runCatching {
            val adJson = remoteConfig[BuildConfig.DEBUG.yes {
                "pl_ad_config_test"
            }.other {
                "pl_ad_config"
            }].asString()
            adJson.isBlank().no {
                AdvertisingHelper.initAdvertising(adJson)
            }
        }

    }
}