package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mycalendar.ui.component.edit.ScheduleInput

@Composable
fun ScheduleAddScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLocationPick: () -> Unit,
    viewModel: ScheduleAddViewModel
) {
    val scheduleEditUiState by viewModel.scheduleEditUiState.collectAsState()
    ScheduleInput(
        editState = scheduleEditUiState.scheduleEditState,
        activity = scheduleEditUiState.activity,
        onActivityChange = { viewModel.onUiStateChange(activity = it) },
        isAllDay = scheduleEditUiState.isAllDay,
        onIsAllDayChange = { viewModel.onUiStateChange(isAllDay = it) },
        onNavigateBack = onNavigateBack,
        onActivitySave = viewModel::onActivityAdd,
        onLocationPick = onNavigateToLocationPick,
    )
}