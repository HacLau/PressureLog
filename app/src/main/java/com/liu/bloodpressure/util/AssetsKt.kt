package com.liu.bloodpressure.util

import android.content.Context
import java.nio.charset.Charset

object AssetsKt {
    fun getJson(context: Context, filename: String): String? {
        kotlin.runCatching {
            return context.assets.open(filename).let {
                val buffer = ByteArray(it.available())
                it.read(buffer)
                it.close()
                String(buffer, Charset.defaultCharset())
            }
        }
        return null
    }
}