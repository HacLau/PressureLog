package com.liu.bloodpressure.advertising

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.liu.bloodpressure.base.BaseAd

class NativeAdvertising(
private val context: Context,
private val type: AdvertisingType,
private val itemAds: AdvertisingItem
) : BaseAd(type, itemAds) {

    private var nativeAd:NativeAd? = null
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()
    override fun loadAd(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit) {
        AdLoader.Builder(context,itemAds.papi).apply {
            forNativeAd {
                nativeAd = it
                loadTime = System.currentTimeMillis()
                onLoad.invoke()
                it.setOnPaidEventListener { value->
                    onPaid(value,item,it.responseInfo)

                }
            }
            withAdListener(object :AdListener(){
                override fun onAdClicked() = AdvertisingHelper.addClick()
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    onLoadFail.invoke(p0.message)
                }
            })
            withNativeAdOptions(NativeAdOptions.Builder().apply {
                setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_LEFT)
            }.build())
        }.build().loadAd(adRequest)
    }

    override fun showAd(activity: Activity, nativeParent: ViewGroup?, onDismiss: () -> Unit) {

    }
}