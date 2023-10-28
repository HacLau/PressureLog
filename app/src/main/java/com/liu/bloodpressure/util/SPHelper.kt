package com.liu.bloodpressure.util

import android.annotation.SuppressLint
import com.liu.bloodpressure.application


object SPHelper {
    var isLaunchedStep by SharedPref(application,"isLaunchedStep",false)
    var isLaunchedStart by SharedPref(application,"isLaunchedStart",false)

    var showCount by SharedPref(application,"showCount",0)
    var clickCount by SharedPref(application,"clickCount",0)

    var showTimes by SharedPref(application,"showTimes",0)
    var showTime by SharedPref(application,"showTime",System.currentTimeMillis())

    var clickTimes by SharedPref(application,"clickTimes",0)
    var clickTime by SharedPref(application,"clickTime",System.currentTimeMillis())
}