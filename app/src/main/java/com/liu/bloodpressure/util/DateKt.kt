package com.liu.bloodpressure.util

import java.util.Calendar

object DateKt {

    fun getYear(time: Long = System.currentTimeMillis()): Int {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return calender.get(Calendar.YEAR)
    }

    fun getMonth(time: Long = System.currentTimeMillis()): Int {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return calender.get(Calendar.MONTH)
    }

    fun getDay(time: Long = System.currentTimeMillis()): Int {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return calender.get(Calendar.DATE)
    }

    fun getHour(time: Long = System.currentTimeMillis()): Int {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return calender.get(Calendar.HOUR_OF_DAY)
    }

    fun getMinute(time: Long = System.currentTimeMillis()): Int {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return calender.get(Calendar.MINUTE)
    }

    fun getDay(year: Int, month: Int): Int {
        val calender = Calendar.getInstance()
        calender.set(year, month, 1)
        calender.add(Calendar.DATE, -1)
        return calender.get(Calendar.DATE)
    }

    fun getDate(time: Long): String {
        val calender = Calendar.getInstance()
        calender.timeInMillis = time
        return "${calender.get(Calendar.YEAR)}-${(calender.get(Calendar.MONTH) + 1).xx()}-${calender.get(Calendar.DATE).xx()} ${calender.get(Calendar.HOUR_OF_DAY).xx()}:${calender.get(Calendar.MINUTE).xx()}"
    }

    fun getMills(year: Int, month: Int, date: Int, hour: Int, minute: Int): Long {
        val calender = Calendar.getInstance()
        calender.set(year, month, date, hour, minute,0)
        return calender.timeInMillis
    }

    fun getMonth(month: Int): String {
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


    fun getTomorrow(): Long {
        val calender = Calendar.getInstance()
        calender.set(getYear(), getMonth() , getDay() + 1 , 0, 0, 0)
        return calender.timeInMillis
    }

    fun getToday(): Long {
        val calender = Calendar.getInstance()
        calender.set(getYear(), getMonth() , getDay() , 0, 0, 0)
        return calender.timeInMillis
    }

    fun getDayOfWeek(dayOfWeek: Int,weekOffset:Int,firstDayOfWeek: Int = Calendar.SUNDAY,):Long{
        val calender = Calendar.getInstance()
        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY){
            return 0L
        }
        if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY){
            return 0L
        }
        calender.firstDayOfWeek = firstDayOfWeek
        calender.add(Calendar.WEEK_OF_MONTH,weekOffset)
        calender.set(Calendar.DAY_OF_WEEK,dayOfWeek)
        calender.set(Calendar.HOUR,0)
        calender.set(Calendar.MINUTE,0)
        calender.set(Calendar.SECOND,0)
        calender.set(Calendar.MILLISECOND,0)
        return calender.timeInMillis

    }

    fun getNextMonth(): Long {
        val calender = Calendar.getInstance()
        calender.set(getYear(), getMonth() + 1, 1, 0, 0, 0)
        return calender.timeInMillis
    }

    fun getThisMonth(): Long {
        val calender = Calendar.getInstance()
        calender.set(getYear(), getMonth(), 1, 0, 0, 0)
        return calender.timeInMillis
    }

    fun getLastMonth(): Long {
        val calender = Calendar.getInstance()
        calender.set(getYear(), getMonth() - 1, 1, 0, 0, 0)
        return calender.timeInMillis
    }


}