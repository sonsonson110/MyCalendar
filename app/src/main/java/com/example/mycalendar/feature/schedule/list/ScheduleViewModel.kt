package com.example.mycalendar.feature.schedule.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.NetworkResult
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
private const val TAG = "ScheduleViewModel"
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _scheduleUiState: MutableStateFlow<ScheduleUiState> =
        MutableStateFlow(ScheduleUiState())
    val scheduleUiState: StateFlow<ScheduleUiState> = _scheduleUiState.asStateFlow()

    init {
        viewModelScope.launch {
            // get activity list
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
        viewModelScope.launch {
            // TODO: get current weather programmatically
            weatherRepository.getCurrentWeather(lon = 20.0, lat = 100.0)
                .catch { e ->
                    _scheduleUiState.update { it.copy(weather = NetworkResult.Error(message = e.message)) }
                }.collect { data ->
                    Log.d(TAG, "getCurrentWeather: $data")
                    _scheduleUiState.update { it.copy(weather = NetworkResult.Success(data = data)) }
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
        // prevent blocking the ui thread (main)
        viewModelScope.launch(Dispatchers.IO) {
            val activity = activityRepository.getActivityDetailById(activityId)
            _scheduleUiState.update {
                it.copy(
                    scheduleDetailUiState = ScheduleDetailUiState(
                        selectedActivity = activity,
                        scheduleState = ScheduleState.SUCCESS
                    )
                )
            }
        }
    }


    fun onActivityDelete() {
        val activity = _scheduleUiState.value.scheduleDetailUiState.selectedActivity
        viewModelScope.launch(Dispatchers.IO) {
            activityRepository.deleteActivity(activity)
            // restore detail idle state
            _scheduleUiState.update {
                it.copy(scheduleDetailUiState = ScheduleDetailUiState())
            }
        }
    }

    fun onMarkAsCompleted() {
        viewModelScope.launch {
            val activity = _scheduleUiState.value.scheduleDetailUiState.selectedActivity
            val newActivity = activity.copy(isCompleted = !activity.isCompleted)

            activityRepository.updateActivity(newActivity)
            // update according to the ui state
            _scheduleUiState.update {
                it.copy(
                    scheduleDetailUiState = it.scheduleDetailUiState.copy(
                        selectedActivity = newActivity
                    )
                )
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val TAG = "ScheduleViewModel"
    }
}