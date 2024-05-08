package com.example.mycalendar.core.database.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DateConverter {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun fromString(value: String?): Date? {
        return value?.let { simpleDateFormat.parse(it) }
    }

    @TypeConverter
    fun toString(value: Date?): String? {
        return value?.let { simpleDateFormat.format(it) }
    }
}