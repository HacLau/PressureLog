package com.liu.bloodpressure.util

import java.util.Calendar

object DateKt {
    private val calender: Calendar by lazy {
        Calendar.getInstance()
    }

    fun getYear(): Int {
        return calender.get(Calendar.YEAR)
    }

    fun getMonth(): Int {
        return calender.get(Calendar.MONTH) + 1
    }

    fun getDay(): Int {
        return calender.get(Calendar.DATE)
    }

    fun getHour(): Int {
        return calender.get(Calendar.HOUR_OF_DAY)
    }

    fun getSecond(): Int {
        return calender.get(Calendar.MINUTE)
    }

    fun getDay(year:Int,month:Int):Int{
        calender.set(year,month,1)
        calender.add(Calendar.DATE,-1)
        return calender.get(Calendar.DATE)
    }

    fun getDate():String{
        return "${getYear()}-${getMonth()}-${getDay()} ${getHour()}:${getSecond()}"
    }

}