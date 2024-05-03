package com.example.mycalendar.core.data.model

import java.util.Date

interface ITask {
    val id: Int
    val title: String?
    val description: String?
    val startTime: Date?
    val type: String?
    val timeZone: String?
    val reminderOffsetSeconds: Int?
    val isCompleted: Boolean?
    val createdUser: User?

    val endTime: Date?
    val colorHex: String?
    val location: Location?
}