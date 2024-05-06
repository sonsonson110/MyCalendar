package com.example.mycalendar.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.R
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.util.toCommonDateOnlyExpression
import com.example.mycalendar.core.data.util.toDayTime
import com.example.mycalendar.feature.schedule.ScheduleState
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.Typography
import com.example.mycalendar.ui.theme.defaultTypeColor
import com.example.mycalendar.ui.theme.urlColor
import java.util.Date

@Composable
fun ScheduleDetailBottomSheetModal(
    activity: Activity,
    scheduleState: ScheduleState,
    navigateToScheduleEdit: (Int) -> Unit,
    onItemDelete: () -> Unit,
    onMarkAsCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gutterWidth = 64.dp
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

            Column(
                modifier = modifier.padding(end = 15.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                        if (activity.startTime != null)
                            Text(
                                text = activity.startTime.toCommonDateOnlyExpression() + " · " + "${activity.startTime.toDayTime()} ${activity.endTime?.let { "- " + it.toDayTime() } ?: ""}",
                                style = Typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // meet link
                if (activity.conferenceUrl != null)
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

                if (activity.location != null)
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
                            Text(
                                text = activity.location.displayName!!,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = Typography.bodyLarge,
                                modifier = Modifier.clickable { /*TODO: Link to google map location*/ }
                            )
                        }
                    )

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
}

@Preview
@Composable
fun ScheduleDetailBottomSheetModalPreview() {
    MyCalendarTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScheduleDetailBottomSheetModal(
                scheduleState = ScheduleState.SUCCESS,
                navigateToScheduleEdit = {},
                onItemDelete = {},
                onMarkAsCompleted = {},
                activity = Activity(
                    isCompleted = true,
                    type = "event",
                    startTime = Date(),
                    endTime = Date(),
                    description = "ActivityActivityActivityActivityActivity",
                    conferenceUrl = "meet.google.com/wjj-pssp-msr",
                    location = Location(displayName = "58 P. Lê Văn Hiến, Đông Ngạc, Bắc Từ Liêm, Hà Nội"),
                    colorHex = 4283405155L,
                    createdUser = User(email = "pson395u9@gmail.com")
                )
            )
        }
    }
}