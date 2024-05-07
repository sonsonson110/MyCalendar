package com.example.mycalendar.feature.schedule.list

import com.example.mycalendar.core.data.model.Activity

data class ScheduleUiState(
    val activities: List<Activity> = emptyList(),
    val scheduleState: ScheduleState = ScheduleState.LOADING,
    val scheduleDetailUiState: ScheduleDetailUiState = ScheduleDetailUiState(),

    )

data class ScheduleDetailUiState(
    val selectedActivity: Activity = Activity(),
    val scheduleState: ScheduleState = ScheduleState.LOADING,
)

enum class ScheduleState {
    LOADING, SUCCESS, EMPTY
}
