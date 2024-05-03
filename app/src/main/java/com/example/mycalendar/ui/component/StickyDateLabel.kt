package com.example.mycalendar.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.core.data.util.toDayInMonth
import com.example.mycalendar.core.data.util.toDayNameInWeek
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import java.util.Date

@Composable
fun StickyDateLabel(date: Date, isToday: Boolean, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            if (!isToday) {
                Text(
                    text = date.toDayNameInWeek(),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = date.toDayInMonth(),
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = date.toDayNameInWeek(),
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .size(36.dp)

                ) {
                    Text(
                        text = date.toDayInMonth(),
                        style = Typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun TodayPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            StickyDateLabel(date = Date(1615642093L), true, modifier = Modifier.size(80.dp))
        }
    }
}
@Composable
@Preview(showBackground = true)
fun NotTodayPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            StickyDateLabel(date = Date(1615642093L), false, modifier = Modifier.size(80.dp))
        }
    }
}