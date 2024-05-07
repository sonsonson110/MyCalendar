package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.repository.UserRepository
import com.example.mycalendar.core.data.util.truncateTimeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository
): ViewModel() {
    var scheduleEditUiState by mutableStateOf(ScheduleEditUiState())
        private set

    init {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            onUiStateChange(activity = scheduleEditUiState.activity.copy(createdUser = user))
        }
    }

    // This is stink
    fun onUiStateChange(activity: Activity = scheduleEditUiState.activity, isAllDay: Boolean = scheduleEditUiState.isAllDay) {
        scheduleEditUiState = scheduleEditUiState.copy(activity = activity, isAllDay = isAllDay)
    }

    suspend fun onActivityAdd() {
        with(scheduleEditUiState) {
            if (isAllDay) {
                scheduleEditUiState = copy(activity = activity.copy(
                    startTime = activity.startTime!!.truncateTimeInfo(),
                    endTime = activity.endTime?.truncateTimeInfo()
                ))
            }
        }
        activityRepository.addActivity(scheduleEditUiState.activity)
    }
}