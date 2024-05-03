package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "EventParticipant", primaryKeys = ["event_id", "participant_uid"],
    foreignKeys = [
        ForeignKey(
            entity = ActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["event_id"],
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["participant_uid"],
        ),
    ])
data class EventUserCrossRef(
    @ColumnInfo(name = "event_id") val eventId: Int,
    @ColumnInfo(name = "participant_uid") val participantId: String,
)
