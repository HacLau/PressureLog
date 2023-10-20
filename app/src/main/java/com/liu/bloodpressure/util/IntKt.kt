package com.liu.bloodpressure.util

import android.content.Context

fun Int.dp2px(context: Context): Int {
    return (context.resources.displayMetrics.density * this).toInt()
}

fun Float.dp2px(context: Context): Float {
    return (context.resources.displayMetrics.density * this)
}