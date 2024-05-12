package com.example.mycalendar.feature.search

import com.example.mycalendar.core.data.model.Location

sealed interface LocationSearchUiState {
    data object Loading : LocationSearchUiState

    data object EmptyQuery : LocationSearchUiState

    data class LoadFailed(val message: String) : LocationSearchUiState

    data class Success(val autocompleteLocations: List<Location> = emptyList()) : LocationSearchUiState {
        fun isEmpty() = autocompleteLocations.isEmpty()
    }
}