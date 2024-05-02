package com.example.mycalendar.core.data.model

import com.example.mycalendar.core.database.model.TaskEntity
import java.util.Date

data class Task(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val startTime: Date? = null,
    val type: String? = null,
    val timeZone: String? = null,
    val reminderOffsetSeconds: Int? = null,
    val isCompleted: Boolean? = null,
    val createdUser: User? = null,
)

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime!!,
    type = this.type!!,
    timeZone = this.timeZone!!,
    reminderOffsetSeconds = this.reminderOffsetSeconds,
    isCompleted = this.isCompleted ?: false,
    createdByUserId = this.createdUser!!.uid!!
)
