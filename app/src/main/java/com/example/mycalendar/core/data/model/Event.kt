package com.example.mycalendar.core.data.model

import java.util.Date

data class Event(
    // Task inheritance properties
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val startTime: Date = Date(),
    val type: String = "",
    val timeZone: String = "",
    val reminderOffsetSeconds: Int? = null,
    val isCompleted: Boolean = false,
    val createdUser: User = User(),
    // End Task inheritance properties

    val endTime: Date = Date(),
    val conferenceUrl: String? = null,
    val colorHex: String? = null,
    val location: Location? = null,
    val participants: List<User> = emptyList(),
)
