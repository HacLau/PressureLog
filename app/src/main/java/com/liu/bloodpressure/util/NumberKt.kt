package com.liu.bloodpressure.util

import android.content.Context
import java.time.LocalDateTime

fun Int.dp2px(context: Context): Int {
    return (context.resources.displayMetrics.density * this).toInt()
}

fun Float.dp2px(context: Context): Float {
    return (context.resources.displayMetrics.density * this)
}

fun Int.px2dp(context: Context):Int{
    return this.div(context.resources.displayMetrics.density).toInt()
}

fun Float.px2dp(context: Context):Float{
    return this.div(context.resources.displayMetrics.density)
}

fun Long.YYmm():String{
    return this.toString()
}

fun Int.xx():String{
    return if (this<10){
        "0$this"
    }else{
        "$this"
    }
}