package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.util.truncateTimeInfo
import com.example.mycalendar.ui.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository: ActivityRepository,
) : ViewModel() {
    private val activityId: Int =
        checkNotNull(savedStateHandle[NavDestination.ScheduleEdit().navArg])
    var scheduleEditUiState by mutableStateOf(ScheduleEditUiState())
        private set

    init {
        viewModelScope.launch {
            val data = activityRepository.getActivityDetailById(activityId)
            scheduleEditUiState = ScheduleEditUiState(activity = data)
        }
    }

    fun onUiStateChange(activity: Activity = scheduleEditUiState.activity, isAllDay: Boolean = scheduleEditUiState.isAllDay) {
        scheduleEditUiState = scheduleEditUiState.copy(activity = activity, isAllDay = isAllDay)
    }

    suspend fun onUpdateActivity() {
        with(scheduleEditUiState) {
            if (isAllDay) {
                scheduleEditUiState = copy(activity = activity.copy(
                    startTime = activity.startTime!!.truncateTimeInfo(),
                    endTime = activity.endTime?.truncateTimeInfo()
                ))
            }
        }
        activityRepository.updateActivity(scheduleEditUiState.activity)
    }
}
