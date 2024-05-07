package com.example.mycalendar.ui.component.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.model.Activity
import java.util.Date

@Composable
fun StickyActivityList(
    state: LazyListState,
    items: List<Activity>,
    modifier: Modifier = Modifier,
    gutterWidth: Dp = 80.dp,
    onFirstVisibleItemDateChange: (Date) -> Unit,
    // the sticky factory lies inside a Box, so BoxScope provides the scope for the parent call implementation
    stickyFactory: @Composable BoxScope.(initial: Date) -> Unit,
    itemFactory: @Composable (Activity) -> Unit,
) {
    // put a Box on the top left gutter, this is where the initial is placed
    Box(modifier = modifier
        .composed {
            var initial: Date? = null

            // iterate through the current visible items on the screen
            state.layoutInfo.visibleItemsInfo.forEachIndexed { index, itemInfo ->
                // collect date of first item
                if (index == 0)
                    onFirstVisibleItemDateChange(items[itemInfo.index].startTime!!)

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
                .padding(start = gutterWidth, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                itemFactory(item)
            }
        }
    }
}