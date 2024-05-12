package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.mycalendar.ui.component.edit.ScheduleInput
import kotlinx.coroutines.launch

@Composable
fun ScheduleAddScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLocationPick: () -> Unit,
    viewModel: ScheduleAddViewModel
) {
    val scheduleEditUiState by viewModel.scheduleEditUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    ScheduleInput(
        activity = scheduleEditUiState.activity,
        onActivityChange = { viewModel.onUiStateChange(activity = it) },
        isAllDay = scheduleEditUiState.isAllDay,
        onIsAllDayChange = { viewModel.onUiStateChange(isAllDay = it) },
        onNavigateBack = onNavigateBack,
        onActivitySave = { coroutineScope.launch { viewModel.onActivityAdd(); onNavigateBack() } },
        onLocationPick = onNavigateToLocationPick,
    )
}