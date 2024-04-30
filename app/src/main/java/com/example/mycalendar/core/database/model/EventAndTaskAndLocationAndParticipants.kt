package com.example.mycalendar.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mycalendar.core.data.model.Event

data class EventAndTaskAndLocationAndParticipants(
    @Embedded val eventEntity: EventEntity,
    @Relation(
        entity = TaskEntity::class,
        parentColumn = "id",
        entityColumn = "id",
    )
    val taskAndUser: TaskAndUser,
    @Relation(
        entity = LocationEntity::class,
        parentColumn = "location_id",
        entityColumn = "place_id",
    )
    val locationEntity: LocationEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "uid",
        associateBy = Junction(
            value = EventUserCrossRef::class,
            parentColumn = "event_id",
            entityColumn = "participant_id"
        )
    )
    val participants: List<UserEntity>,
)


fun EventAndTaskAndLocationAndParticipants.toEvent() = Event(
    id = this.taskAndUser.taskEntity.id,
    title = this.taskAndUser.taskEntity.title,
    description = this.taskAndUser.taskEntity.description,
    startTime = this.taskAndUser.taskEntity.startTime,
    type = this.taskAndUser.taskEntity.type,
    timeZone = this.taskAndUser.taskEntity.timeZone,
    reminderOffsetSeconds = this.taskAndUser.taskEntity.reminderOffsetSeconds,
    isCompleted = this.taskAndUser.taskEntity.isCompleted,
    createdUser = this.taskAndUser.userEntity.toUser(),

    endTime = this.eventEntity.endTime,
    conferenceUrl = this.eventEntity.conferenceUrl,
    colorHex = this.eventEntity.colorHex,
    location = this.locationEntity.toLocation(),

    participants = this.participants.map(UserEntity::toUser)
)