package com.example.mycalendar.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.model.Task
import java.util.Date

@Composable
fun <T : Task> StickyTaskList(
    items: List<T>,
    modifier: Modifier = Modifier,
    gutterWidth: Dp = 80.dp,
    stickyFactory: @Composable BoxScope.(initial: Date) -> Unit,
    itemFactory: @Composable (T) -> Unit,
) {
    val state: LazyListState = rememberLazyListState()

    Box(modifier = modifier
        .composed {
            var initial: Date? = null
            state.layoutInfo.visibleItemsInfo.forEachIndexed { index, itemInfo ->

                val itemInitial = items[itemInfo.index].startTime

                if (itemInitial != initial) {
                    initial = itemInitial
                    val nextInitial = items.getOrNull(itemInfo.index + 1)?.startTime
                    val offsetDp = with(LocalDensity.current) {
                        itemInfo.offset.toDp()
                    }
                    Box(
                        modifier = if (index != 0 || itemInitial != nextInitial) {
                            Modifier.offset(x = 0.dp, y = offsetDp)
                        } else Modifier,
                    ) {
                        stickyFactory(initial = itemInitial!!)
                    }
                }
            }
            this
        }
        // prevent initial leaking out side the view
        .clip(RectangleShape)) {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = gutterWidth)
        ) {
            items(items) { item ->
                itemFactory(item)
            }
        }
    }
}

@Preview
@Composable
fun StickyTaskListPreview() {
    val taskList = mutableListOf<Task>()
    for (i in 1..10) {
        taskList.add(
            Task(
                title = "Task number 1",
                description = null,
                startTime = Date(1715642093L),
                type = "task",
                timeZone = "Asia/Ho_Chi_Minh",
                reminderOffsetSeconds = 600,
                isCompleted = false,
            )
        )
    }
    for (i in 1..10) {
        taskList.add(
            Task(
                title = "Task number 1",
                description = null,
                startTime = Date(1815642093L),
                type = "task",
                timeZone = "Asia/Ho_Chi_Minh",
                reminderOffsetSeconds = 600,
                isCompleted = false,
            )
        )
    }
    for (i in 1..10) {
        taskList.add(
            Task(
                title = "Task number 1",
                description = null,
                startTime = Date(1615642093L),
                type = "task",
                timeZone = "Asia/Ho_Chi_Minh",
                reminderOffsetSeconds = 600,
                isCompleted = false,
            )
        )
    }
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            // simulate an top app bar
            .padding(top = 80.dp)
    ) {
        StickyTaskList(
            items = taskList,
            stickyFactory = { date ->
                Box(modifier = Modifier.width(80.dp)) {
                    Text(text = date.toString(), modifier = Modifier.align(Alignment.Center))
                }
            },
            itemFactory = { task ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = task.toString())
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            },
        )
    }
}