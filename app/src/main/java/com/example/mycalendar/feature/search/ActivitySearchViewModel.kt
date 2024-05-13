package com.example.mycalendar.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.repository.ActivityRepository
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
import javax.inject.Inject

private const val TAG = "ActivitySearchViewModel"

@HiltViewModel
class ActivitySearchViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val activitySearchUiState: StateFlow<ActivitySearchUiState> =
        _searchQuery
            // prevent methods after `.debounce()` execute when typing too fast
            .debounce(QUERY_DEBOUNCE_MILLIS)
            .flatMapLatest { query ->
                if (query.length < MIN_QUERY_LENGTH)
                    flowOf(ActivitySearchUiState.EmptyQuery)
                else {
                    activityRepository.getFilterPlainLocalActivityList(query)
                        .map<List<Activity>, ActivitySearchUiState> { data ->
                            ActivitySearchUiState.Success(activities = data)
                        }
                        .catch { e ->
                            Log.d(TAG, e.toString())
                        }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                ActivitySearchUiState.Loading
            )
    fun onSearchQueryChanged(query: String) {
        _searchQuery.update { query }
    }
    companion object {
        private const val MIN_QUERY_LENGTH = 2
        private const val TIMEOUT_MILLIS = 5000L
        private const val QUERY_DEBOUNCE_MILLIS = 500L
    }
}