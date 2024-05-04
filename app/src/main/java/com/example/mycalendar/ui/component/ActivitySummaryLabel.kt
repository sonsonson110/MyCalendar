package com.example.mycalendar.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.util.toDayTime
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import java.util.Date

@Composable
fun ActivitySummaryLabel(
    activity: Activity,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color(activity.colorHex ?: 4283668945))
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 4.dp, bottom = 12.dp)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = activity.title ?: "No title",
                style = Typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.W600,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            if (activity.type == "event") {
                Text(
                    text = activity.startTime!!.toDayTime() + " - " + activity.endTime!!.toDayTime(),
                    style = Typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
                if (activity.location != null)
                    Text(
                        text = activity.location.displayName!!,
                        style = Typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
            } else
                Text(
                    text = activity.startTime!!.toDayTime(),
                    style = Typography.bodyLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TaskActivitySummaryLabelPreview() {
    MyCalendarTheme {
        ActivitySummaryLabel(
            activity = Activity(
                type = "task",
                startTime = Date(2000, 10, 14, 10, 14, 20)
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun EventActivitySummaryLabelPreview() {
    MyCalendarTheme {
        ActivitySummaryLabel(
            activity = Activity(
                title = "EventActivitySummaryLabelPreviewEventActivitySummaryLabelPreviewEventActivitySummaryLabelPreviewEventActivitySummaryLabelPreview",
                type = "event",
                colorHex = 4292183162L,
                startTime = Date(2000, 10, 14, 10, 14, 20),
                endTime = Date(2000, 10, 14, 13, 14, 20),
                location = Location(displayName = "Hoc vien hoang gia")
            )
        )
    }
}