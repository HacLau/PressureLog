package com.liu.bloodpressure.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.liu.bloodpressure.model.Record

@Dao
interface RecordDao {
    @Query("select * from record order by changeTime desc")
    fun queryAll():List<Record>

    @Update
    fun updateRecord(record: Record)

    @Insert
    fun insertRecord(record: Record)
}