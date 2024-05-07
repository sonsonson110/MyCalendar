package com.example.mycalendar.feature.schedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.repository.UserRepository
import com.example.mycalendar.ui.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    private val activityId: Int? = savedStateHandle[NavDestination.ScheduleEdit().navArg]
    var scheduleEditUiState by mutableStateOf(ScheduleEditUiState())
        private set

    init {
        if (activityId != null) {
            val activityDetail = activityRepository.getActivityDetailById(activityId)
            scheduleEditUiState = scheduleEditUiState.copy(activity = activityDetail)
        } else {
            scheduleEditUiState = scheduleEditUiState.copy(activity = scheduleEditUiState.activity.copy())
        }
    }
}