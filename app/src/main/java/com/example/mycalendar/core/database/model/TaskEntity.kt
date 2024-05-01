package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.mycalendar.core.data.model.Task
import java.util.Date

@Entity(tableName = "Task",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["created_by_user_id"],
        )])
data class TaskEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "start_time") val startTime: Date,
    val type: String,
    @ColumnInfo(name = "time_zone") val timeZone: String,
    @ColumnInfo(name = "reminder_offset_seconds") val reminderOffsetSeconds: Int?,
    @ColumnInfo(name = "is_completed", defaultValue = "0") val isCompleted: Boolean,
    @ColumnInfo(name = "created_by_user_id") val createdByUserId: String,
)