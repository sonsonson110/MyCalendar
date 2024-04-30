package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "EventParticipant", primaryKeys = ["event_id", "participant_id"],
    foreignKeys = [
        ForeignKey(
            entity = EventEntity::class,
            parentColumns = ["id"],
            childColumns = ["event_id"],
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["uid"],
            childColumns = ["participant_id"],
        ),
    ])
data class EventUserCrossRef(
    @ColumnInfo(name = "event_id") val eventId: Int,
    @ColumnInfo(name = "participant_id") val participantId: String,
)
