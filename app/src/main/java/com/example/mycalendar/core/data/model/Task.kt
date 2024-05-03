package com.example.mycalendar.core.data.model

import com.example.mycalendar.core.database.model.TaskEntity
import java.util.Date

data class Task(
    override val id: Int = 0,
    override val title: String? = null,
    override val description: String? = null,
    override val startTime: Date? = null,
    override val type: String? = null,
    override val timeZone: String? = null,
    override val reminderOffsetSeconds: Int? = null,
    override val isCompleted: Boolean? = null,
    override val createdUser: User? = null,

    override val endTime: Date? = null,
    override val colorHex: String? = null,
    override val location: Location? = null,
): ITask

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
