package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Event",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["place_id"],
            childColumns = ["location_id"],
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
        ),
    ]
)
data class EventEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "end_time") val endTime: Date,
    @ColumnInfo(name = "conference_url") val conferenceUrl: String?,
    @ColumnInfo(name = "color_hex") val colorHex: String?,
    @ColumnInfo(name = "location_id") val locationId: Int?,
)
