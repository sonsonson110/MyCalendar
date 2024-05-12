package com.example.mycalendar.ui.component.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.R
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.util.addByHour
import com.example.mycalendar.core.data.util.setTimeInfo
import com.example.mycalendar.core.data.util.toMinute
import com.example.mycalendar.core.data.util.toSecond
import com.example.mycalendar.core.data.util.updateDateWithMillis
import com.example.mycalendar.ui.component.NoDecorationTextField
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.defaultTypeColor
import java.util.Date

val colorHexNameHashMap = hashMapOf(
    0xffde583c to "Tomato",
    0xffe16c41 to "Tangerine",
    0xffe8ba58 to "Banana",
    0xff4f9363 to "Basil",
    0xffd5847a to "Flamingo",
    0xff5399d1 to "Default color"
)

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleInput(
    onNavigateBack: () -> Unit,
    activity: Activity,
    onActivityChange: (Activity) -> Unit,
    onActivitySave: () -> Unit,
    modifier: Modifier = Modifier,
    isCreate: Boolean = true,
    isAllDay: Boolean = false,
    onIsAllDayChange: (Boolean) -> Unit,
    onLocationPick: () -> Unit,
) {
    val gutterWidth = 64.dp
    Column(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // header buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "close",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { onNavigateBack() })
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onActivitySave,
                enabled = activity.isDateRangeValid()
            ) {
                Text(text = "Save", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.width(15.dp))
        }

        // title input
        ScheduleDetailFieldTemplate(
            icon = { Spacer(modifier = Modifier.width(gutterWidth)) },
            items = {
                NoDecorationTextField(
                    value = activity.title ?: "",
                    onValueChange = {
                        onActivityChange(activity.copy(title = it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                    placeholder = {
                        Text(
                            "Add title",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                )
                if (isCreate)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        FilterChip(
                            selected = activity.type == "task",
                            onClick = { onActivityChange(activity.copy(type = "task")) },
                            label = {
                                Text(text = "Task")
                            })
                        FilterChip(
                            selected = activity.type == "event",
                            onClick = { onActivityChange(activity.copy(type = "event")) },
                            label = {
                                Text(text = "Event")
                            })
                    }
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // account selection
        ScheduleDetailFieldTemplate(
            verticalAlignment = Alignment.CenterVertically,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "profile",
                    modifier = Modifier
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            items = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(activity.colorHex?.let { Color(it) } ?: defaultTypeColor)
                    )
                    Text(
                        text = activity.type!!.capitalize(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = activity.createdUser?.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        ScheduleDetailFieldTemplate(
            verticalAlignment = Alignment.CenterVertically,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_clock_24),
                    contentDescription = "profile",
                    modifier = Modifier
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }, items = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onIsAllDayChange(!isAllDay) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "All day",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f, true)
                    )
                    // this remove extra top padding of the default switch
                    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                        Switch(
                            checked = isAllDay,
                            onCheckedChange = { onIsAllDayChange(it) },
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // start date & time selection
        DateTimeSelectionField(
            date = activity.startTime!!,
            onDatePicked = {
                onActivityChange(
                    activity.copy(
                        startTime = activity.startTime.updateDateWithMillis(
                            it
                        )
                    )
                )
            },
            onTimePicked = { hour, minute ->
                onActivityChange(
                    activity.copy(
                        startTime = activity.startTime.setTimeInfo(
                            hour,
                            minute
                        )
                    )
                )
            },
            isAllDay = isAllDay,
            gutterWidth = gutterWidth,
        )

        if (activity.type == "event") {
            Spacer(modifier = Modifier.height(4.dp))
            DateTimeSelectionField(
                date = activity.endTime!!,
                onDatePicked = {
                    onActivityChange(
                        activity.copy(
                            endTime = activity.endTime.updateDateWithMillis(
                                it
                            )
                        )
                    )
                },
                onTimePicked = { hour, minute ->
                    onActivityChange(
                        activity.copy(
                            endTime = activity.endTime.setTimeInfo(
                                hour,
                                minute
                            )
                        )
                    )
                },
                isAllDay = isAllDay,
                gutterWidth = gutterWidth,
            )
        }



        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // timezone info
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

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // conference url field
        if (activity.type == "event") {
            ScheduleDetailFieldTemplate(
                verticalAlignment = Alignment.CenterVertically,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_videocam_24),
                        contentDescription = "video conferencing",
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                items = {
                    NoDecorationTextField(
                        value = activity.conferenceUrl ?: "",
                        onValueChange = { onActivityChange(activity.copy(conferenceUrl = it)) },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Add video conferencing url",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        })
                }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        if (activity.type == "event") {
            ScheduleDetailFieldTemplate(
                verticalAlignment = Alignment.CenterVertically,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                items = {
                    Text(
                        text = activity.location?.displayName ?: "Add location",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { onLocationPick() }
                    )
                }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        if (activity.type == "event") {
            var openColorPickerDialog by remember { mutableStateOf(false) }

            ScheduleDetailFieldTemplate(
                verticalAlignment = Alignment.CenterVertically,
                icon = {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(activity.colorHex?.let { Color(it) } ?: defaultTypeColor)
                    )
                },
                items = {
                    Text(
                        text = activity.colorHex?.let { colorHexNameHashMap[it] }
                            ?: "Default color",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { openColorPickerDialog = true }
                    )
                }
            )
            if (openColorPickerDialog)
                PickerDialog(
                    onDismissRequest = { openColorPickerDialog = false },
                    content = {
                        colorHexNameHashMap.forEach { (colorHexKey, colorName) ->
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onActivityChange(activity.copy(colorHex = colorHexKey))
                                        openColorPickerDialog = false
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(Color(colorHexKey))
                                ) {
                                    if (colorHexKey != activity.colorHex && !(activity.colorHex == null && colorHexKey == 0xff5399d1)) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.surface)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                                Text(
                                    text = colorName,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }

        // notification set
        var openNotificationPickerDialog by remember { mutableStateOf(false) }
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
                    text = activity.reminderOffsetSeconds?.let { "Before ${it.toMinute()} minutes" }
                        ?: "Add notification",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable { openNotificationPickerDialog = true }
                )
            }
        )
        if (openNotificationPickerDialog)
            PickerDialog(
                onDismissRequest = { openNotificationPickerDialog = false },
                content = {
                    val minuteOptions = listOf(5, 10, 15, 20, 30)
                    minuteOptions.forEach { minute ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onActivityChange(activity.copy(reminderOffsetSeconds = minute.toSecond()))
                                    openNotificationPickerDialog = false
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            RadioButton(
                                selected = activity.reminderOffsetSeconds == minute.toSecond(),
                                onClick = {
                                    onActivityChange(activity.copy(reminderOffsetSeconds = minute.toSecond()))
                                    openNotificationPickerDialog = false
                                },
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Before $minute minutes",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // description input
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
                NoDecorationTextField(
                    value = activity.description ?: "",
                    onValueChange = { onActivityChange(activity.copy(description = it)) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    placeholder = {
                        Text(
                            text = "Add description",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }, singleLine = false
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun ScheduleInputPreview() {
    MyCalendarTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val date = Date()
            ScheduleInput(activity = Activity(
                type = "event",
                startTime = date,
                endTime = date.addByHour(1),
                createdUser = User(email = "pson34587q349@gmai.com")
            ),
                onActivityChange = {},
                onNavigateBack = {},
                onActivitySave = {},
                onIsAllDayChange = {},
                onLocationPick = {})
        }
    }
}