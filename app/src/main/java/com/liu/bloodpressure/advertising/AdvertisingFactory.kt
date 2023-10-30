package com.liu.bloodpressure.advertising

import android.content.Context
import android.view.ViewGroup
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.base.BaseAd
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AdvertisingFactory(private val type: AdvertisingType) {

    private val source: MutableList<AdvertisingItem> = mutableListOf()
    val cache: MutableList<BaseAd> = mutableListOf()
    private var loading = false
    private var onLoad: (Boolean) -> Unit = {}
    fun initSource(list: MutableList<AdvertisingItem>?) {
        source.run {
            source.clear()
            source.addAll(list ?: mutableListOf())
            sortByDescending {
                it.devote
            }
        }
    }

    private fun isCacheOverTime(): Boolean {
        val item = cache.firstOrNull() ?: return false
        return (System.currentTimeMillis() - item.loadTime >= item.item.remedy * 6000L).yes {
            cache.remove(item)
            true
        }.other {
            false
        }
    }

    fun load(context: Context) {
        CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.message?.logE()
        }).launch {
            "Advertising ${type.type} source size ${source.size}".logE()
            "Advertising ${type.type} over count ${AdvertisingHelper.overCount()}".logE()
            "Advertising ${type.type} cache ${cache.size} and isCacheOverTime = ${isCacheOverTime()}".logE()
            "Advertising ${type.type} loading ? = ${loading}".logE()
            source.isEmpty()
                .or(AdvertisingHelper.overCount())
                .or(cache.isNotEmpty() && isCacheOverTime().not())
                .or(loading).yes {
                    return@launch
                }
            loading = true
            AdvertisingLoader(context, type, source) {bool,baseAd ->
                loading = false
                onLoad.invoke(bool)
                if (bool) {
                    cache.add(baseAd!!)
                    cache.sortByDescending {
                        it.item.devote
                    }
                }
            }.startLoad()
        }
    }

    fun withLoad(context: Context, load: (Boolean) -> Unit = {}) {
        (cache.isNotEmpty() && isCacheOverTime().not()).yes {
            load.invoke(true)
        }.other {
            onLoad = load
            load(context)
        }
    }

    fun showFullScreen(activity: BaseActivity, onDismiss: () -> Unit) {
        cache.isEmpty().yes {
            onDismiss.invoke()
        }.other {
            cache.removeFirstOrNull()?.showAd(activity, onDismiss = onDismiss) ?: { onDismiss.invoke() }
        }
        onLoad = {}
        load(context = activity)
    }

    fun showNative(activity: BaseActivity, parent: ViewGroup, onBaseAd: (BaseAd) -> Unit) {
        cache.removeFirstOrNull()?.let {
            it.showAd(activity, parent)
            onBaseAd.invoke(it)
        }
        onLoad = {}
        load(context = activity)
    }

}