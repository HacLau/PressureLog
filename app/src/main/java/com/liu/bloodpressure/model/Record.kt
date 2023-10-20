package com.liu.bloodpressure.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class BloodEntity(
    var type: Int = -1,
    var mainTop: News? = null,
    var news: News? = null,
    var record: Record? = null,
    var historyTop: HistoryTop? = null,
    var settingTop: Setting? = null,
    var setting: Setting? = null
)

@Parcelize
data class News(
    var title: String,
    var content: String,
    @DrawableRes
    var iconUrl: Int,
    var from: String,
) : Parcelable

@Parcelize
@Entity
data class Record(
    var systolic: Int,
    var diastolic: Int,
    var degree: Int,
    @PrimaryKey
    var recordTime: String,
    var changeTime:Long
) : Parcelable

data class HistoryTop(
    var systolic: Int,
    var diastolic: Int
)

data class Setting(
    var title: String,
    var type: Int,
    var content: String,
    @DrawableRes
    var nextIcon: Int = -1
)


object ItemType {
    val MAINTOP = 1
    val NEWS = 2
    val RECORD = 3
    val HISTORYTOP = 4
    val SETTINGTOP = 5
    val SETTING = 6
}

object Degree {
    val NORMAL = "normal"
    val RELAXED = "relaxed"
    val ORDINARY = "ordinary"
    val SEVERITY = "severity"
    val DANGER = "danger"
    val DESTORY = "destory"
}
