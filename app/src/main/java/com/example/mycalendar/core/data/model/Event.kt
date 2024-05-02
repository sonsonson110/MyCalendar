package com.example.mycalendar.core.data.model

import java.util.Date

data class Event(
    // Task inheritance properties
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val startTime: Date? = null,
    val type: String? = null,
    val timeZone: String? = null,
    val reminderOffsetSeconds: Int? = null,
    val isCompleted: Boolean = false,
    val createdUser: User? = null,
    // End Task inheritance properties

    val endTime: Date? = null,
    val conferenceUrl: String? = null,
    val colorHex: String? = null,
    val location: Location? = null,
    val participants: List<User>? = null,
)
