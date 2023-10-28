package com.liu.bloodpressure.advertising

import android.content.Context
import com.liu.bloodpressure.base.BaseAd
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes

class AdvertisingLoader(
    private val context: Context,
    private val type: AdvertisingType,
    private val source: MutableList<AdvertisingItem>,
    private val cache: MutableList<BaseAd>,
    private val onLoad: (Boolean) -> Unit = {}
) {
    fun startLoad() {
        source.isEmpty().yes {
            onLoad.invoke(false)
        }.other {
            load(0)
        }
    }

    private fun load(index: Int) {
        source.getOrNull(index)?.let { item ->

            "Advertising loading adType = ${type.type} id = ${item.papi} type = ${item.ostentatious}".logE()
            when (item.ostentatious) {
                AdvertisingItemType.op, AdvertisingItemType.inter -> FullScreenAdvertising(context, type, item)
                AdvertisingItemType.native -> NativeAdvertising(context, type, item)
                else -> null
            }.let { baseAd ->
                (baseAd == null).yes {
                    onLoad.invoke(false)
                }.other {
                    baseAd?.loadAd(onLoad = {
                        "Advertising load Success adType = ${type.type} id = ${item.papi} type = ${item.ostentatious}".logE()
                        cache.add(baseAd)
                        cache.sortByDescending {
                            it.item.devote
                        }
                    }, onLoadFail = {
                        "Advertising load Fail adType = ${type.type}  id = ${item.papi} type = ${item.ostentatious}".logE()
                        load(index + 1)
                    })
                }
            }
        } ?: {
            onLoad.invoke(false)
        }
    }

}