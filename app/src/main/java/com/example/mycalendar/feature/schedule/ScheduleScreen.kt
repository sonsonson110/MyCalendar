package com.example.mycalendar.feature.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycalendar.R
import com.example.mycalendar.core.data.util.isEqualIgnoreTimeTo
import com.example.mycalendar.core.data.util.toMonthName
import com.example.mycalendar.ui.component.ScheduleDetailBottomSheetModal
import com.example.mycalendar.ui.component.ScheduleItem
import com.example.mycalendar.ui.component.StickyActivityList
import com.example.mycalendar.ui.component.StickyDateLabel
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import java.util.Date

private const val TAG = "ScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToScheduleEdit: (Int?) -> Unit,
) {
    val scheduleUiState by viewModel.scheduleUiState.collectAsState()
    var currentDate by remember { mutableStateOf(Date()) }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    // to control the scrolling behavior from parent
    val listState = rememberLazyListState()


    Scaffold(
        topBar = { ScheduleTopBar(date = currentDate) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToScheduleEdit(null) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add an activity")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (scheduleUiState.scheduleState == ScheduleState.LOADING)
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

            // prevent empty list appears on screen and breaks app logic
            if (scheduleUiState.scheduleState == ScheduleState.SUCCESS) {

                val gutterWidth = 64.dp

                StickyActivityList(
                    state = listState,
                    items = scheduleUiState.activities,
                    gutterWidth = gutterWidth,
                    onFirstVisibleItemDateChange = { currentDate = it },
                    stickyFactory = { date ->
                        StickyDateLabel(
                            date = date,
                            isToday = Date().isEqualIgnoreTimeTo(date),
                            modifier = Modifier.width(gutterWidth)
                        )
                    },
                    itemFactory = { activity ->
                        ScheduleItem(
                            activity = activity,
                            onClick = {
                                viewModel.onScheduleItemClick(activity.id)
                                showBottomSheet = true
                            }
                        )
                    },
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    // Model sheet content
                    ScheduleDetailBottomSheetModal(
                        scheduleState = scheduleUiState.scheduleDetailUiState.scheduleState,
                        activity = scheduleUiState.scheduleDetailUiState.selectedActivity,
                        navigateToScheduleEdit = navigateToScheduleEdit,
                        onItemDelete = {
                            viewModel.onActivityDelete()
                            showBottomSheet = false
                        },
                        onMarkAsCompleted = viewModel::onMarkAsCompleted
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTopBar(
    date: Date,
) {
    TopAppBar(
        title = {
            Text(
                date.toMonthName(),
                style = Typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(24.dp)
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_outline_today_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 12.dp, end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}

@Preview
@Composable
fun ScheduleScreenPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScheduleScreen(
                navigateToScheduleEdit = { })
        }
    }
}