package com.liu.bloodpressure.advertising

import androidx.annotation.Keep

@Keep
data class AdvertisingEntity(
    var gybb: Int = 0,
    var illut: Int = 0,
    var pl_launch: MutableList<AdvertisingItem>? = null,
    var pl_record_int: MutableList<AdvertisingItem>? = null,
    var pl_history_nat: MutableList<AdvertisingItem>? = null,
    var pl_alarm_nat: MutableList<AdvertisingItem>? = null
)
@Keep
data class AdvertisingItem(
    var papi: String,
    var confectionery: String,
    var ostentatious: String,
    var remedy: Int,
    var devote: Int
)

enum class AdvertisingType(val type: String) {
    LAUNCH("pl_launch"),
    RECORD("pl_record_int"),
    HISTORY("pl_history_nat"),
    ALARM("pl_alarm_nat")
}

object AdvertisingItemType {
    const val op = "op"
    const val inter = "int"
    const val native = "nat"
}

object AdvertisingPlatform {
    const val ADS_PLAT_ADMOB = "admob"
    const val ADS_PLAT_MAX = "max"
    const val ADS_PLAT_TOPON = "topon"
    const val ADS_PLAT_TRADPLUS = "tradplus"
}

