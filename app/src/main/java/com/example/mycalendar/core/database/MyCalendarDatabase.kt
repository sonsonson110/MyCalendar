package com.example.mycalendar.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycalendar.core.database.dao.EventDao
import com.example.mycalendar.core.database.dao.TaskDao
import com.example.mycalendar.core.database.model.EventEntity
import com.example.mycalendar.core.database.model.EventUserCrossRef
import com.example.mycalendar.core.database.model.LocationEntity
import com.example.mycalendar.core.database.model.TaskEntity
import com.example.mycalendar.core.database.model.UserEntity
import com.example.mycalendar.core.database.util.DateConverter

@Database(
    version = 1,
    entities = [
        TaskEntity::class,
        EventEntity::class,
        LocationEntity::class,
        UserEntity::class,
        EventUserCrossRef::class,
    ]
)
@TypeConverters(DateConverter::class)
abstract class MyCalendarDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun eventDao(): EventDao
}