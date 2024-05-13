package com.example.mycalendar.feature.search

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycalendar.core.data.util.isEqualIgnoreTimeTo
import com.example.mycalendar.ui.component.SearchView
import com.example.mycalendar.ui.component.schedule.ScheduleItem
import com.example.mycalendar.ui.component.schedule.StickyActivityList
import com.example.mycalendar.ui.component.schedule.StickyDateLabel
import java.util.Date

@Composable
fun ActivitySearchScreen(
    onNavigateBack: () -> Unit,
    viewModel: ActivitySearchViewModel = hiltViewModel()
) {
    val query by viewModel.searchQuery.collectAsState()
    val activitySearchUiState by viewModel.activitySearchUiState.collectAsState()

    val gutterWidth = 64.dp
    // to control the scrolling behavior from parent
    val listState = rememberLazyListState()

    SearchView(
        placeHolder = "Search activities",
        onNavigateBack = onNavigateBack,
        query = query,
        onQueryChange = viewModel::onSearchQueryChanged
    ) {
        Spacer(modifier = Modifier.height(4.dp)) // create top bar space

        if (activitySearchUiState is ActivitySearchUiState.Success) {
            if (!(activitySearchUiState as ActivitySearchUiState.Success).isEmpty()) {
                StickyActivityList(
                    state = listState,
                    items = (activitySearchUiState as ActivitySearchUiState.Success).activities,
                    gutterWidth = gutterWidth,
                    onFirstVisibleItemDateChange = {},
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
//                                viewModel.onScheduleItemClick(activity.id)  // get the detail activity value
//                                showBottomSheet = true
                            }
                        )
                    },
                )
            } else {
                Text(
                    text = "We found nothing, try to search with another keyword\n🫠",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}