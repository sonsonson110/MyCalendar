package com.example.mycalendar.feature.schedule.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycalendar.R
import com.example.mycalendar.core.data.util.findIndexOfClosestDateFromList
import com.example.mycalendar.core.data.util.isEqualIgnoreTimeTo
import com.example.mycalendar.core.data.util.toMonthName
import com.example.mycalendar.ui.component.schedule.ScheduleDetailBottomSheetModal
import com.example.mycalendar.ui.component.schedule.ScheduleItem
import com.example.mycalendar.ui.component.schedule.ScheduleModalNavigationDrawer
import com.example.mycalendar.ui.component.schedule.StickyActivityList
import com.example.mycalendar.ui.component.schedule.StickyDateLabel
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import kotlinx.coroutines.launch
import java.util.Date

private const val TAG = "ScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navigateToScheduleEdit: (Int) -> Unit,
    navigateToScheduleAdd: () -> Unit,
) {
    val scheduleUiState by viewModel.scheduleUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var currentDate by remember { mutableStateOf(Date()) }

    // to control the drawer from the left
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // to control the bottom sheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    // to control the scrolling behavior from parent
    val listState = rememberLazyListState()

    // snack bar
    val snackbarHostState = remember { SnackbarHostState() }

    ScheduleModalNavigationDrawer(weather = scheduleUiState.weather, drawerState = drawerState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                ScheduleTopBar(
                    date = currentDate,
                    onDrawerToggle = {
                        coroutineScope.launch {
                            drawerState.apply { if (isClosed) open() else close() }
                        }
                    },
                    onTodayScroll = {
                        if (scheduleUiState.activities.isEmpty())
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("There is nothing to do today. Create one!")
                                return@launch
                            }

                        val today = Date()
                        val todayIndex = findIndexOfClosestDateFromList(
                            today,
                            scheduleUiState.activities.map { it.startTime!! })

                        coroutineScope.launch {
                            listState.scrollToItem(todayIndex)
                            if (!scheduleUiState.activities[todayIndex].startTime!!.isEqualIgnoreTimeTo(today))
                                snackbarHostState.showSnackbar("There is nothing to do today. Create one!")
                        }


                    })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = navigateToScheduleAdd) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add an activity")
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                if (scheduleUiState.scheduleState == ScheduleState.LOADING)
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                if (scheduleUiState.scheduleState == ScheduleState.EMPTY) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Create your first activity at the right bottom button!\nヾ(•ω•`)o",
                            style = Typography.headlineSmall,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                // prevent empty list appears on screen and breaks app logic
                if (scheduleUiState.scheduleState == ScheduleState.SUCCESS) {
                    val gutterWidth = 64.dp

                    // go to today or find the closest Date and prompt no schedule
                    LaunchedEffect(key1 = Unit) {
                        if (scheduleUiState.activities.isNotEmpty()) {
                            val index = findIndexOfClosestDateFromList(
                                Date(),
                                scheduleUiState.activities.map { it.startTime!! })
                            listState.scrollToItem(index = index)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

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
                                    viewModel.onScheduleItemClick(activity.id)  // get the detail activity value
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
                            navigateToScheduleEdit = {
                                showBottomSheet = false
                                navigateToScheduleEdit(it)
                            },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTopBar(
    date: Date,
    onDrawerToggle: () -> Unit,
    onTodayScroll: () -> Unit,
) {
    Surface(shadowElevation = 3.dp) {
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
                        .clickable { onDrawerToggle() }
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
                    modifier = Modifier
                        .padding(start = 12.dp, end = 16.dp)
                        .clickable { onTodayScroll() }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
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
                navigateToScheduleEdit = { }, navigateToScheduleAdd = {})
        }
    }
}