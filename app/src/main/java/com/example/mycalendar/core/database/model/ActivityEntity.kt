package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.model.User
import java.util.Date

@Entity(
    tableName = "Activity",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["place_id"],
            childColumns = ["place_id"],
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["created_by_uid"],
        )
    ],
    // for faster list queries
    indices = [Index(value = ["start_time"])]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "start_time") val startTime: Date,
    val type: String,
    @ColumnInfo(name = "time_zone") val timeZone: String,
    @ColumnInfo(name = "reminder_offset_seconds") val reminderOffsetSeconds: Int?,
    @ColumnInfo(name = "is_completed", defaultValue = "0") val isCompleted: Boolean,
    @ColumnInfo(name = "created_by_uid") val createdByUid: String,
    @ColumnInfo(name = "end_time") val endTime: Date?,
    @ColumnInfo(name = "conference_url") val conferenceUrl: String?,
    @ColumnInfo(name = "color_hex") val colorHex: Long?,
    @ColumnInfo(name = "place_id") val placeId: Long?,
)

fun ActivityEntity.toActivity() = Activity(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime,
    type = this.type,
    timeZone = this.timeZone,
    reminderOffsetSeconds = this.reminderOffsetSeconds,
    isCompleted = this.isCompleted,
    createdUser = User(uid = this.createdByUid),
    endTime = this.endTime,
    conferenceUrl = this.conferenceUrl,
    colorHex = this.colorHex,
    location = Location(placeId = this.placeId)
)