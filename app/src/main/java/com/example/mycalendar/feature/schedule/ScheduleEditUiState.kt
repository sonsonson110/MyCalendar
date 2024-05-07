package com.example.mycalendar.feature.schedule

import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.util.addByHour
import java.util.Date
import java.util.TimeZone

data class ScheduleEditUiState(
    val activity: Activity = Activity(
        type = "task",
        startTime = Date(),
        endTime = Date().addByHour(1),
        timeZone = TimeZone.getDefault().id
    ),
    val isAllDay: Boolean = false
)
