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
import com.example.mycalendar.core.data.model.Activity
import java.util.Date

@Composable
fun StickyTaskList(
    items: List<Activity>,
    modifier: Modifier = Modifier,
    gutterWidth: Dp = 80.dp,
    // the sticky factory lies inside a Box, so BoxScope provides the scope for the parent call implementation
    stickyFactory: @Composable BoxScope.(initial: Date) -> Unit,
    itemFactory: @Composable (Activity) -> Unit,
) {
    val state: LazyListState = rememberLazyListState()

    // put a Box on the top left gutter, this is where the initial is placed
    Box(modifier = modifier
        .composed {
            var initial: Date? = null

            // iterate through the current visible items on the screen
            state.layoutInfo.visibleItemsInfo.forEachIndexed { index, itemInfo ->

                // the current initial of the item
                val itemInitial = items[itemInfo.index].startTime

                // if the current iterating item is different initial from the last item's...
                //  else show NO initial for that item
                if (itemInitial != initial) {
                    //  1. replace the last initial
                    initial = itemInitial

                    //  2. get the info of the next item
                    val nextInitial = items.getOrNull(itemInfo.index + 1)?.startTime

                    //  3. get the offset of the current item, turn it into 'dp' metric
                    val offsetDp = with(LocalDensity.current) {
                        itemInfo.offset.toDp()
                    }
                    //  4. if the current item is not the first item in visible items, or
                    // the next initial is different from the current initial
                    Box(
                        modifier = if (index != 0 || itemInitial != nextInitial) {
                            // the initial y-axis value is now following the item offset
                            Modifier.offset(x = 0.dp, y = offsetDp)
                        } else {
                            // or continue sticking to the top left
                            Modifier
                        },
                    ) {
                        stickyFactory(itemInitial!!)
                    }
                }
            }
            this
        }
        // prevent initial leaking outside the view
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
//    val taskList = mutableListOf<ITask>()
//    for (i in 1..10) {
//        taskList.add(
//            Task(
//                title = "Task number 1",
//                description = null,
//                startTime = Date(1715642093L),
//                type = "task",
//                timeZone = "Asia/Ho_Chi_Minh",
//                reminderOffsetSeconds = 600,
//                isCompleted = false,
//            )
//        )
//    }
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            // simulate an top app bar
            .padding(top = 80.dp)
    ) {
        StickyTaskList(
            items = emptyList(),
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