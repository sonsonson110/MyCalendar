package com.example.mycalendar.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mycalendar.core.database.dao.ActivityDao
import com.example.mycalendar.core.database.dao.UserDao
import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.model.EventUserCrossRef
import com.example.mycalendar.core.database.model.LocationEntity
import com.example.mycalendar.core.database.model.UserEntity
import com.example.mycalendar.core.database.util.DateConverter

@Database(
    version = 1,
    entities = [
        ActivityEntity::class,
        LocationEntity::class,
        UserEntity::class,
        EventUserCrossRef::class,
    ]
)
@TypeConverters(DateConverter::class)
abstract class MyCalendarDatabase: RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun userDao(): UserDao
}