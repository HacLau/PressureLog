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
    var recordTop: RecordTop? = null,
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
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var systolic: Int = 0,
    var diastolic: Int = 0,
    var degree: Int = 0,
    var showTime: Long = 0L
) : Parcelable

data class RecordTop(
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




