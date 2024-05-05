package com.example.mycalendar.feature.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    private val _scheduleUiState: MutableStateFlow<ScheduleUiState> =
        MutableStateFlow(ScheduleUiState())
    val scheduleUiState: StateFlow<ScheduleUiState> = _scheduleUiState.asStateFlow()

    init {
        viewModelScope.launch {
            activityRepository.getAllPlainActivity()
                .collect { list ->
                    _scheduleUiState.update {
                        it.copy(
                            activities = list,
                            scheduleState = ScheduleState.SUCCESS
                        )
                    }
                }
        }
    }

    fun onScheduleItemClick(activityId: Int) {
        _scheduleUiState.update {
            it.copy(
                scheduleDetailUiState = ScheduleDetailUiState(
                    scheduleState = ScheduleState.LOADING
                )
            )
        }
        viewModelScope.launch {
            activityRepository.getActivityDetailById(activityId).collect { data ->
                _scheduleUiState.update {
                    it.copy(
                        scheduleDetailUiState = ScheduleDetailUiState(
                            scheduleState = ScheduleState.SUCCESS,
                            selectedActivity = data,
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val TAG = "ScheduleViewModel"
    }
}