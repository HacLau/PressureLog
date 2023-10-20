package com.liu.bloodpressure.model

import androidx.annotation.DrawableRes

data class StepEntity(
    var title:String,
    var content:String,
    @DrawableRes
    var imageUrl:Int
)
