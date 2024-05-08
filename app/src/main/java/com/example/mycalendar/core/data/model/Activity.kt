package com.example.mycalendar.core.data.model

import com.example.mycalendar.core.database.model.ActivityEntity
import com.example.mycalendar.core.database.util.DateConverter
import java.util.Date

data class Activity(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val startTime: Date? = null,
    val type: String? = null,
    val timeZone: String? = null,
    val reminderOffsetSeconds: Int? = null,
    val isCompleted: Boolean = false,
    val createdUser: User? = null,

    val endTime: Date? = null,
    val conferenceUrl: String? = null,
    val colorHex: Long? = null,
    val location: Location? = null,
    val participants: List<User>? = null,
) {
    fun isDateRangeValid(): Boolean {
        if (type == "event")
            return startTime!!.before(endTime) || startTime.equals(endTime)
        return true
    }
}

fun Activity.toActivityEntity() = ActivityEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime!!,
    type = this.type!!,
    timeZone = this.timeZone!!,
    reminderOffsetSeconds = this.reminderOffsetSeconds,
    isCompleted = this.isCompleted,
    createdByUid = this.createdUser!!.uid!!,
    endTime = this.endTime,
    conferenceUrl = this.conferenceUrl,
    colorHex = this.colorHex,
    placeId = this.location?.placeId
)

fun Activity.toHashMap() = hashMapOf(
    "id" to this.id,
    "title" to this.title,
    "description" to this.description,
    // util from room type converter
    "startTime" to DateConverter().toString(this.startTime),
    "type" to this.type,
    "timeZone" to this.timeZone,
    "reminderOffsetSeconds" to this.reminderOffsetSeconds,
    "isCompleted" to this.isCompleted,
    "createdUser" to this.createdUser!!.toUserMap(),

    "endTime" to DateConverter().toString(this.endTime),
    "conferenceUrl" to this.conferenceUrl,
    "colorHex" to this.colorHex,
    "location" to this.location?.toLocationHashMap(),
    "participants" to this.participants?.map { it.toUserMap() }
)
