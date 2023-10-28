package com.liu.bloodpressure.base

import android.app.Activity
import android.view.ViewGroup
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.ResponseInfo
import com.liu.bloodpressure.advertising.AdvertisingItem
import com.liu.bloodpressure.advertising.AdvertisingType

abstract class BaseAd(
    private val type: AdvertisingType,
    val item: AdvertisingItem,
    var loadTime: Long = System.currentTimeMillis()
) {
    abstract fun loadAd(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit = {})
    abstract fun showAd(activity: Activity, nativeParent: ViewGroup? = null, onDismiss: () -> Unit = {})

    fun onPaid(adValue: AdValue, item: AdvertisingItem, responseInfo: ResponseInfo?) {

    }
}