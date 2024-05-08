package com.example.mycalendar.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleDetailFieldTemplate(
    icon: @Composable BoxScope.() -> Unit,
    items: @Composable ColumnScope.() -> Unit,
    gutterWidth: Dp = 56.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
) {
    Row(modifier = modifier, verticalAlignment = verticalAlignment) {
        Box(
            modifier = Modifier.width(gutterWidth),
            content = icon
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            content = items,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
    }
}