package com.example.mycalendar.ui.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate

@Composable
fun LocationItem(location: Location,) {
    ScheduleDetailFieldTemplate(
        verticalAlignment = Alignment.CenterVertically,
        icon = {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "location",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        },
        items = {
            Column {
                Text(
                    text = location.getDisplayPlace()!!,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = location.getDisplayAddress()!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}