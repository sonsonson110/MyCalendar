package com.example.mycalendar.feature.schedule.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.repository.LocationRepository
import com.example.mycalendar.core.data.util.setTimeInfo
import com.example.mycalendar.core.data.util.truncateTimeInfo
import com.example.mycalendar.feature.search.LocationSearchUiState
import com.example.mycalendar.ui.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository: ActivityRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val activityId: Int =
        checkNotNull(savedStateHandle[NavDestination.ScheduleEdit().navArg])

    private val _scheduleEditUiState = MutableStateFlow(ScheduleEditUiState())
    var scheduleEditUiState = _scheduleEditUiState.asStateFlow()

    // for location selection
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val locationSearchUiState : StateFlow<LocationSearchUiState> =
        _searchQuery
            // prevent methods after `.debounce()` execute when typing too fast
            .debounce(QUERY_DEBOUNCE_MILLIS)
            .flatMapLatest { query ->
                if (query.length < MIN_QUERY_LENGTH)
                    flowOf(LocationSearchUiState.EmptyQuery)
                else {
                    locationRepository.getAutocompleteLocationsFromNetwork(query)
                        .map<List<Location>, LocationSearchUiState> { data ->
                            LocationSearchUiState.Success(autocompleteLocations = data)
                        }
                        .catch { e ->
                            emit(LocationSearchUiState.LoadFailed(e.message ?: "Unexpected error"))
                        }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                LocationSearchUiState.Loading
            )

    init {
        viewModelScope.launch {
            val data = activityRepository.getLocalActivityDetailById(activityId)
            _scheduleEditUiState.update { it.copy(activity = data) }
        }
    }

    fun onUiStateChange(
        activity: Activity = _scheduleEditUiState.value.activity,
        isAllDay: Boolean = _scheduleEditUiState.value.isAllDay
    ) {
        _scheduleEditUiState.update { it.copy(activity = activity, isAllDay = isAllDay) }
    }

    suspend fun onUpdateActivity() {
        var finalStartTime = scheduleEditUiState.value.activity.startTime
        var finalEndTime = scheduleEditUiState.value.activity.takeIf { it.type == "event" }?.endTime

        if (scheduleEditUiState.value.isAllDay) {
            finalStartTime = finalStartTime!!.truncateTimeInfo()
            finalEndTime = finalEndTime?.setTimeInfo(23, 59)
        }

        val newActivity = scheduleEditUiState.value.activity.copy(
            startTime = finalStartTime,
            endTime = finalEndTime
        )
        newActivity.location?.let { locationRepository.addLocalLocation(it) }
        activityRepository.updateLocalActivity(newActivity)
        activityRepository.updateRemoteActivity(newActivity)
    }

    // ----------- Location search methods ---------------------------------------------------------

    // scope the function to the screen lifecycle only
    fun onSearchQueryChanged(query: String) {
        _searchQuery.update { query }
    }

    fun onLocationSelected(location: Location) {
        val currentActivity = _scheduleEditUiState.value.activity
        onUiStateChange(activity = currentActivity.copy(location = location))
    }
    companion object {
        private const val MIN_QUERY_LENGTH = 3
        private const val TIMEOUT_MILLIS = 5000L
        private const val QUERY_DEBOUNCE_MILLIS = 500L
    }
}
