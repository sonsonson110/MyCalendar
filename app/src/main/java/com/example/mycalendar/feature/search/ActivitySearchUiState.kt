package com.example.mycalendar.feature.search

import com.example.mycalendar.core.data.model.Activity

sealed interface ActivitySearchUiState{
    data object Loading: ActivitySearchUiState
    data object EmptyQuery: ActivitySearchUiState

    data class Success(val activities: List<Activity> = emptyList()): ActivitySearchUiState {
        fun isEmpty() = activities.isEmpty()
    }
}
