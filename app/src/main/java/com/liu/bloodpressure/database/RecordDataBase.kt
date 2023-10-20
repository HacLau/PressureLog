package com.liu.bloodpressure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liu.bloodpressure.model.Record

@Database(entities = [Record::class], version = 1)
abstract class RecordDataBase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        private var INSTANCE: RecordDataBase? = null
        fun getDatabase(context: Context): RecordDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDataBase::class.java,
                    "record_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}