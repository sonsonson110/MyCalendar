package com.example.mycalendar.ui.component.schedule

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.mycalendar.R
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.util.toCommonDateOnlyExpression
import com.example.mycalendar.core.data.util.toDayTime
import com.example.mycalendar.core.data.util.toMinute
import com.example.mycalendar.feature.schedule.list.ScheduleState
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate
import com.example.mycalendar.ui.theme.Typography
import com.example.mycalendar.ui.theme.defaultTypeColor
import com.example.mycalendar.ui.theme.urlColor

@Composable
fun ScheduleDetailBottomSheetModal(
    activity: Activity,
    scheduleState: ScheduleState,
    navigateToScheduleEdit: (Int) -> Unit,
    onItemDelete: () -> Unit,
    onMarkAsCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val gutterWidth = 56.dp
    Column(
        modifier = modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (scheduleState == ScheduleState.LOADING)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        if (scheduleState == ScheduleState.SUCCESS) {
            // variants
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "edit",
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp)
                        .clickable { navigateToScheduleEdit(activity.id) },
                )

                var expanded by remember { mutableStateOf(false) }

                // surround with box so that popup show at parent position
                Box {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "more context",
                        modifier = Modifier
                            .padding(start = 12.dp, end = 16.dp)
                            .size(24.dp)
                            .clickable { expanded = true },
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = { onItemDelete(); expanded = false }
                        )
                    }
                }
            }

            Text(
                text = activity.type!!.capitalize(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = gutterWidth)
            )

            // title
            ScheduleDetailFieldTemplate(
                icon = {
                    Box(modifier = Modifier
                        .padding(top = 16.dp)
                        .size(16.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(activity.colorHex?.let { Color(it) } ?: defaultTypeColor)
                    )
                },
                items = {
                    Text(
                        text = activity.title ?: "No title",
                        style = Typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (activity.isCompleted) TextDecoration.LineThrough else null,
                    )

                    // time
                    if (activity.startTime != null) {
                        val startDate =
                            activity.startTime.toCommonDateOnlyExpression() + " · " + activity.startTime.toDayTime()
                        val endDate =
                            activity.endTime?.let { it.toCommonDateOnlyExpression() + " · " + it.toDayTime() }
                        Text(
                            text = startDate,
                            style = Typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (endDate != null)
                            Text(
                                text = endDate,
                                style = Typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // meet link
            if (activity.conferenceUrl != null) {
                ScheduleDetailFieldTemplate(
                    verticalAlignment = Alignment.CenterVertically,
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.google_meet_logo),
                            contentDescription = "note",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp),
                        )
                    },
                    items = {
                        Text(
                            text = activity.conferenceUrl,
                            color = urlColor,
                            style = Typography.bodyLarge,
                            textDecoration = TextDecoration.Underline,
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // location
            if (activity.location != null) {
                ScheduleDetailFieldTemplate(
                    verticalAlignment = Alignment.CenterVertically,
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.google_map_logo),
                            contentDescription = "note",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp),
                        )
                    },
                    items = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = activity.location.getDisplayPlace()!!,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = activity.location.getDisplayAddress()!!,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.icon_open_in_new_24),
                                contentDescription = "open",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable { openGoogleMapByLocation(activity.location, context) }
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (activity.reminderOffsetSeconds != null) {
                ScheduleDetailFieldTemplate(
                    verticalAlignment = Alignment.CenterVertically,
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "notify",
                            modifier = Modifier
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    items = {
                        Text(
                            text = "Before ${activity.reminderOffsetSeconds.toMinute()} minutes",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            ScheduleDetailFieldTemplate(
                verticalAlignment = Alignment.CenterVertically,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_globe_24),
                        contentDescription = "profile",
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }, items = {
                    Text(
                        text = activity.timeZone!!,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // account
            ScheduleDetailFieldTemplate(
                verticalAlignment = Alignment.CenterVertically,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_outline_today_24),
                        contentDescription = "note",
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                items = {
                    Text(
                        text = activity.createdUser!!.email!!,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Typography.bodyLarge
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (activity.description != null)
                ScheduleDetailFieldTemplate(
                    verticalAlignment = Alignment.CenterVertically,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_notes_24),
                            contentDescription = "note",
                            modifier = Modifier
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    items = {
                        Text(
                            text = activity.description,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = Typography.bodyLarge
                        )
                    }
                )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = if (activity.isCompleted) "Mark as completed" else "Mark uncompleted",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = Typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp)
                    .clickable { onMarkAsCompleted() },
            )
        }
    }
}

private fun openGoogleMapByLocation(location: Location, context: Context) {
    val mapIntentUri = Uri.parse("geo:0,0?q=${location.lat},${location.lon}")
    val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}