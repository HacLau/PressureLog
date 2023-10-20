package com.liu.bloodpressure.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.liu.bloodpressure.model.Record

@Dao
interface RecordDao {
    @Query("select * from record order by showTime desc")
    fun queryAll(): List<Record>

    @Query("select * from record where recordTime between datetime('now','start of day','+1 seconds') and  datetime('now','start of day','+1 days','-1 seconds') order by showTime desc")
    fun queryRecent(): List<Record>

    @Query("select * from record where recordTime between datetime('now','weekday 0','-6 days') and  datetime('now','weekday 0','+1 days') order by showTime desc ")
    fun queryWeek(): List<Record>

    @Query("select * from record  where strftime('%Y-%m', 'now') = strftime('%Y-%m',recordTime) order by showTime desc ")
    fun queryMonth(): List<Record>

    @Query("select * from record where strftime('%Y-%m', 'now','-1 month') = strftime('%Y-%m',recordTime) order by showTime desc ")
    fun queryLastMonth(): List<Record>

    @Query("select * from record where strftime('%Y', 'now') = strftime('%Y',recordTime) order by showTime desc ")
    fun queryYear(): List<Record>

    @Update
    fun updateRecord(record: Record)

    @Insert
    fun insertRecord(record: Record)
}