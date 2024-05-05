package com.example.mycalendar.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mycalendar.core.data.model.Activity

data class ActivityWithLocationAndUserAndParticipants(
    @Embedded val activityEntity: ActivityEntity,
    @Relation(
        entity = LocationEntity::class,
        parentColumn = "place_id",
        entityColumn = "place_id",
    )
    val locationEntity: LocationEntity? = null,
    @Relation(
        entity = UserEntity::class,
        parentColumn = "created_by_uid",
        entityColumn = "uid",
    )
    val userEntity: UserEntity,
    @Relation(
        associateBy = Junction(
            value = EventUserCrossRef::class,
            parentColumn = "event_id",
            entityColumn = "participant_uid"
        ),
        parentColumn = "id",
        entityColumn = "uid",
    )
    val participants: List<UserEntity> = emptyList()
)

fun ActivityWithLocationAndUserAndParticipants.toActivity() = Activity(
    id = this.activityEntity.id,
    title = this.activityEntity.title,
    description = this.activityEntity.description,
    startTime = this.activityEntity.startTime,
    type = this.activityEntity.type,
    timeZone = this.activityEntity.timeZone,
    reminderOffsetSeconds = this.activityEntity.reminderOffsetSeconds,
    isCompleted = this.activityEntity.isCompleted,
    createdUser = this.userEntity.toUser(),
    endTime = this.activityEntity.endTime,
    conferenceUrl = this.activityEntity.conferenceUrl,
    colorHex = this.activityEntity.colorHex,
    location = locationEntity?.toLocation(),
    participants = this.participants.map(UserEntity::toUser)
)
