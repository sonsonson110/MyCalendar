package com.example.mycalendar.feature.schedule.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycalendar.ui.component.edit.ScheduleInput
import kotlinx.coroutines.launch

@Composable
fun ScheduleAddScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScheduleAddViewModel = hiltViewModel()
) {
    val scheduleEditUiState = viewModel.scheduleEditUiState
    val coroutineScope = rememberCoroutineScope()
    ScheduleInput(
        activity = scheduleEditUiState.activity,
        onActivityChange = { viewModel.onUiStateChange(activity = it) },
        isAllDay = scheduleEditUiState.isAllDay,
        onIsAllDayChange = { viewModel.onUiStateChange(isAllDay = it) },
        onNavigateBack = onNavigateBack,
        onActivitySave = { coroutineScope.launch { viewModel.onActivityAdd(); onNavigateBack() } }
    )
}