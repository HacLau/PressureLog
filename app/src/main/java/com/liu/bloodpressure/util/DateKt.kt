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
        return calender.get(Calendar.MONTH)
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
        return "${getYear().xx()}-${(getMonth() + 1).xx()}-${getDay().xx()} ${getHour().xx()}:${getSecond().xx()}"
    }

    fun getDate(time:Long):String{
        calender.timeInMillis = time
        return "${getYear()}-${(getMonth() + 1).xx()}-${getDay().xx()} ${getHour().xx()}:${getSecond().xx()}"
    }

    fun getMills(time: String): Long {
        time.replace("-", " ").replace(":", " ").split(" ").let {
            calender.set(it[0].toInt(),it[1].toInt() - 1,it[2].toInt(),it[3].toInt(),it[4].toInt())
            return calender.timeInMillis
        }
    }

    fun getMonth(month:Int):String{
        return when (month) {
            1 -> {
                "Jan"
            }

            2 -> {
                "Feb"
            }

            3 -> {
                "Mar"
            }

            4 -> {
                "Apr"
            }

            5 -> {
                "May"
            }

            6 -> {
                "Jun"
            }

            7 -> {
                "Jul"
            }

            8 -> {
                "Aug"
            }

            9 -> {
                "Sept"
            }

            10 -> {
                "Oct"
            }

            11 -> {
                "Nov"
            }

            12 -> {
                "Dec"
            }

            else -> {
                ""
            }
        }
    }

}