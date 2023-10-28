package com.liu.bloodpressure.advertising

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.view.ViewParent
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
    private val cache: MutableList<BaseAd> = mutableListOf()
    private var loading = false
    private var onLoad: (Boolean) -> Unit = {}
    val cacheIsNotEmpty = cache.isNotEmpty()
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
            if (source.isEmpty()) return@launch
            if (AdvertisingHelper.overCount()) return@launch
            if (cacheIsNotEmpty && isCacheOverTime().not()) return@launch
            if (loading) return@launch
            loading = true
            AdvertisingLoader(context, type, source, cache) {
                loading = false
                onLoad.invoke(it)
            }.startLoad()
        }
    }

    fun withLoad(context: Context, load: (Boolean) -> Unit = {}) {
        (cacheIsNotEmpty && isCacheOverTime().not()).yes {
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

    fun showNative(activity: BaseActivity,parent: ViewGroup,onBaseAd:(BaseAd)->Unit){
        cache.removeFirstOrNull()?.let {
            it.showAd(activity,parent)
            onBaseAd.invoke(it)
        }
        onLoad = {}
        load(context = activity)
    }

}