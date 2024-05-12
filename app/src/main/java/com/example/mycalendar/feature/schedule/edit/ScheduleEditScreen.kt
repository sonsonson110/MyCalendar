package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.mycalendar.ui.component.edit.ScheduleInput
import kotlinx.coroutines.launch

@Composable
fun ScheduleEditScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLocationPick: () -> Unit,
    viewModel: ScheduleEditViewModel,
) {
    val scheduleEditUiState by viewModel.scheduleEditUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    ScheduleInput(
        isCreate = false,
        activity = scheduleEditUiState.activity,
        onActivityChange = { viewModel.onUiStateChange(activity = it) },
        isAllDay = scheduleEditUiState.isAllDay,
        onIsAllDayChange = { viewModel.onUiStateChange(isAllDay = it) },
        onNavigateBack = onNavigateBack,
        onActivitySave = { coroutineScope.launch { viewModel.onUpdateActivity(); onNavigateBack() } },
        onLocationPick = onNavigateToLocationPick,
    )
}


