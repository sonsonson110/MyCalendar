package com.example.mycalendar.core.data.model

import java.util.Date

data class Event(
    // Task inheritance properties
    override val id: Int = 0,
    override val title: String? = null,
    override val description: String? = null,
    override val startTime: Date? = null,
    override val type: String? = null,
    override val timeZone: String? = null,
    override val reminderOffsetSeconds: Int? = null,
    override val isCompleted: Boolean = false,
    override val createdUser: User? = null,
    // End Task inheritance properties

    override val endTime: Date? = null,
    val conferenceUrl: String? = null,
    override val colorHex: String? = null,
    override val location: Location? = null,
    val participants: List<User>? = null,
): ITask
