package com.liu.bloodpressure.net

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.liu.bloodpressure.application
import com.liu.bloodpressure.util.logE

object FireBaseHelper {
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(application) }
    fun updateEvent(eventName: String, paramMap: MutableMap<String, Any?>){
        "Advertising FireBaseHelper updateEvent ${eventName}".logE()
        kotlin.runCatching {
            firebaseAnalytics.logEvent(eventName, Bundle().apply {
                for (entry in paramMap.entries) {
                    when (val value = entry.value) {
                        is Int -> putInt(entry.key, value)
                        is String -> putString(entry.key, value)
                        is Double -> putDouble(entry.key, value)
                        is Long -> putLong(entry.key, value)
                        else -> Unit
                    }
                }
            })
        }
    }
}