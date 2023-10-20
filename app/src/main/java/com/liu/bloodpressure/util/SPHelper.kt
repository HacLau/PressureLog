package com.liu.bloodpressure.util

import android.annotation.SuppressLint
import com.liu.bloodpressure.application


object SPHelper {
    var isLaunchedStep by SharedPref(application,"isLaunchedStep",false)
}