package com.liu.bloodpressure.advertising

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.base.BaseAd
import com.liu.bloodpressure.net.FireBaseHelper
import com.liu.bloodpressure.util.logE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FullScreenAdvertising(
    private val context: Context,
    private val type: AdvertisingType,
    private val itemAds: AdvertisingItem
) : BaseAd(type, itemAds) {

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()
    private var advertising: Any? = null

    private var onLoad: () -> Unit = {}
    private var onLoadFail: (msg: String?) -> Unit = {}
    override fun loadAd(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit) {
        when(item.ostentatious){
            AdvertisingItemType.op ->loadOpen(onLoad,onLoadFail)
            AdvertisingItemType.inter ->loadInter(onLoad,onLoadFail)
            AdvertisingItemType.native ->loadNative(onLoad,onLoadFail)
            else-> onLoadFail.invoke("ad type is not right")
        }
    }

    private fun loadNative(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit) {
        this.onLoad = onLoad
        this.onLoadFail = onLoadFail
    }

    private fun loadInter(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit) {
        this.onLoad = onLoad
        this.onLoadFail = onLoadFail
        InterstitialAd.load(context, itemAds.papi, adRequest,interCallback)
    }

    private fun loadOpen(onLoad: () -> Unit, onLoadFail: (msg: String?) -> Unit) {
        this.onLoad = onLoad
        this.onLoadFail = onLoadFail
        AppOpenAd.load(context,itemAds.papi,adRequest,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,openCallback)
    }

    override fun showAd(activity: Activity, nativeParent: ViewGroup?, onDismiss: () -> Unit) {
        fun onAdsClose(){
            val baseActivity = activity as? BaseActivity
            if (null != baseActivity){
                baseActivity.lifecycleScope.launch {
                    "baseActivity.isActivityOnResume() = ${baseActivity.isActivityOnResume()}".logE()
                    while (!baseActivity.isActivityOnResume()) delay(200L)
                    onDismiss.invoke()
                }
            }else{
                onDismiss.invoke()
            }

        }
        val callback: FullScreenContentCallback by lazy {
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    onAdsClose()
                }

                override fun onAdShowedFullScreenContent() {
                    AdvertisingHelper.addShow()
                }

                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    onAdsClose()
                }

                override fun onAdClicked() {
                    AdvertisingHelper.addClick()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }
            }
        }

        when(val ad = advertising){
            is AppOpenAd->{
                ad.run {
                    fullScreenContentCallback = callback
                    show(activity)
                }
            }
            is InterstitialAd ->{
                ad.run {
                    fullScreenContentCallback = callback
                    show(activity)
                }
            }
            else -> {
                "not type of item adv".logE()
                onDismiss.invoke()
            }
        }
        FireBaseHelper.updateEvent("pl_ad_impression", mutableMapOf(
            "pl_ad_id" to type.type
        ))

    }

    private val openCallback = object :AppOpenAd.AppOpenAdLoadCallback(){
        override fun onAdLoaded(p0: AppOpenAd) {
            advertising = p0
            loadTime = System.currentTimeMillis()
            onLoad.invoke()
            p0.setOnPaidEventListener {
                onPaid(it,item,p0.responseInfo)
            }
        }

        override fun onAdFailedToLoad(p0: LoadAdError) {
            onLoadFail.invoke(p0.message)
        }
    }
    private val interCallback = object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            advertising = interstitialAd
            loadTime = System.currentTimeMillis()
            onLoad.invoke()
            interstitialAd.setOnPaidEventListener {
                onPaid(it, item, interstitialAd.responseInfo)
            }
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            "onAdFailedToLoad ${loadAdError.message}".logE()
            onLoadFail.invoke(loadAdError.message)
        }
    }


}