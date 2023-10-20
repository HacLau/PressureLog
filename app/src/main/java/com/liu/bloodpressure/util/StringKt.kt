package com.liu.bloodpressure.util


import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "StringKt"
fun String.logE() {
    Log.e(TAG, this)
}

fun String.logI() {
    Log.i(TAG, this)
}

fun String.logD() {
    Log.d(TAG, this)
}

fun String.logW() {
    Log.w(TAG, this)
}

fun String.logV() {
    Log.v(TAG, this)
}

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
