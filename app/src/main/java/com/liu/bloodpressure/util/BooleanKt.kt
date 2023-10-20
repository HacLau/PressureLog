package com.liu.bloodpressure.util

sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data: T) : BooleanExt<T>()

inline fun <T : Any> Boolean.yes(block: () -> T) =
    when {
        this -> {
            WithData(block())
        }

        else -> {
            Otherwise
        }
    }

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> {
        WithData(block())
    }
}
inline fun <T> BooleanExt<T>.other(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is WithData -> this.data
    }

