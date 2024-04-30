package com.example.mycalendar.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mycalendar.core.data.model.Task

data class TaskAndUser(
    @Embedded val taskEntity: TaskEntity,
    @Relation(
        entity = UserEntity::class,
        parentColumn = "created_by_user_id",
        entityColumn = "uid"
    )
    val userEntity: UserEntity
)

fun TaskAndUser.toTask() = Task(
    id = this.taskEntity.id,
    title = this.taskEntity.title,
    description = this.taskEntity.description,
    startTime = this.taskEntity.startTime,
    type = this.taskEntity.type,
    timeZone = this.taskEntity.timeZone,
    reminderOffsetSeconds = this.taskEntity.reminderOffsetSeconds,
    isCompleted = this.taskEntity.isCompleted,
    createdUser = this.userEntity.toUser(),
)