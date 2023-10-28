package com.liu.bloodpressure.advertising

import com.google.gson.Gson
import com.liu.bloodpressure.util.DateKt
import com.liu.bloodpressure.util.SPHelper
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes

object AdvertisingHelper {
    var showCount = 0
    var clickCount = 0
    val launchAd: AdvertisingFactory = AdvertisingFactory(AdvertisingType.LAUNCH)
    val recordAd: AdvertisingFactory = AdvertisingFactory(AdvertisingType.RECORD)
    val historyAd: AdvertisingFactory = AdvertisingFactory(AdvertisingType.HISTORY)
    val alarmAd: AdvertisingFactory = AdvertisingFactory(AdvertisingType.ALARM)
    private var entity: AdvertisingEntity? = null

    fun initAdvertising(json: String?) {
        "Advertising init json data".logE()
        entity = Gson().fromJson(json, AdvertisingEntity::class.java)
        showCount = entity?.gybb ?: 0
        clickCount = entity?.illut ?: 0
        launchAd.initSource(entity?.pl_launch)
        recordAd.initSource(entity?.pl_record_int)
        historyAd.initSource(entity?.pl_history_nat)
        alarmAd.initSource(entity?.pl_alarm_nat)
    }

    fun overCount(): Boolean {
        return overClick() || overShow()
    }

    private fun overShow(): Boolean {
        "Advertising current showTimes = ${SPHelper.showTimes}".logE()
        return (showCount == 0).yes {
            false
        }.other {
            DateKt.isToday(SPHelper.showTime).yes {
                SPHelper.showTimes >= showCount
            }.other {
                false
            }
        }
    }

    private fun overClick(): Boolean {

        "Advertising current clickTimes = ${SPHelper.clickTimes}".logE()
        return (clickCount == 0).yes { false }.other {
            DateKt.isToday(SPHelper.clickTime).yes {
                SPHelper.clickTimes >= clickCount
            }.other {
                false
            }
        }
    }

    fun addShow() {
        "Advertising show Success current showTimes = ${SPHelper.showTimes} add 1".logE()
        kotlin.runCatching {
            DateKt.isToday(SPHelper.showTime).yes {
                SPHelper.showTimes++
            }.other {
                SPHelper.showTimes = 1
                SPHelper.showTime = System.currentTimeMillis()
            }
        }
    }

    fun addClick() {
        "Advertising was Clicked current clickTimes = ${SPHelper.clickTimes} add 1".logE()
        kotlin.runCatching {
            DateKt.isToday(SPHelper.clickTime).yes {
                SPHelper.clickTimes++
            }.other {
                SPHelper.clickTimes = 1
                SPHelper.clickTime = System.currentTimeMillis()
            }
        }
    }


}