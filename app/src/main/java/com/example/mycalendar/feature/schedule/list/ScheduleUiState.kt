package com.example.mycalendar.feature.schedule.list

import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.NetworkResult
import com.example.mycalendar.core.data.model.Weather

data class ScheduleUiState(
    val activities: List<Activity> = emptyList(),
    val scheduleState: ScheduleState = ScheduleState.LOADING,
    val scheduleDetailUiState: ScheduleDetailUiState = ScheduleDetailUiState(),
    val weather: NetworkResult<Weather> = NetworkResult.Empty(),
)

data class ScheduleDetailUiState(
    val selectedActivity: Activity = Activity(),
    val scheduleState: ScheduleState = ScheduleState.LOADING,
)

enum class ScheduleState {
    LOADING, SUCCESS, EMPTY
}
