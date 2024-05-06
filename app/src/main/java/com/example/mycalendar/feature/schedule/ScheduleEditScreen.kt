package com.example.mycalendar.feature.schedule

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycalendar.R
import com.example.mycalendar.core.data.util.setTimeInfo
import com.example.mycalendar.core.data.util.toCommonDateOnlyExpression
import com.example.mycalendar.core.data.util.toDayTime
import com.example.mycalendar.core.data.util.truncateTimeInfo
import com.example.mycalendar.core.data.util.updateDateWithMillis
import com.example.mycalendar.ui.component.NoDecorationTextField
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate
import com.example.mycalendar.ui.component.TimePickerDialog
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.example.mycalendar.ui.theme.defaultTypeColor
import java.util.Date
import java.util.TimeZone

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleEditScreen(
    modifier: Modifier = Modifier
) {
    val gutterWidth = 64.dp
    Column(
        modifier = modifier
            .padding(top = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // title input
        var title by remember { mutableStateOf("") }
        // type selection group
        var type by remember { mutableStateOf("task") }

        ScheduleDetailFieldTemplate(
            icon = { Spacer(modifier = Modifier.width(gutterWidth)) },
            items = {
                NoDecorationTextField(
                    value = title,
                    onValueChange = { title = it },
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    FilterChip(
                        selected = type == "task",
                        onClick = { type = "task" },
                        label = {
                            Text(text = "Task")
                        })
                    FilterChip(
                        selected = type == "event",
                        onClick = { type = "event" },
                        label = {
                            Text(text = "Event")
                        })
                }
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // account selection
        var colorHex by remember { mutableStateOf(defaultTypeColor) }
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
                            .background(colorHex)
                    )
                    Text(
                        text = type.capitalize(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "pson1478236sdf@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        var date by remember { mutableStateOf(Date()) }
        var switchState by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = switchState) {
            if (switchState)
                date = date.truncateTimeInfo()
        }

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
                        .clickable { switchState = !switchState },
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
                            checked = switchState,
                            onCheckedChange = { switchState = !switchState },
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // date time selection
        val datePickerState = rememberDatePickerState()
        var showDatePicker by remember { mutableStateOf(false) }

        val timePickerState = rememberTimePickerState(
            initialHour = date.hours,
            initialMinute = date.minutes,
            is24Hour = false
        )
        var showTimePicker by remember { mutableStateOf(false) }

        ScheduleDetailFieldTemplate(
            icon = { Spacer(modifier = Modifier.width(gutterWidth)) },
            items = {
                Row {
                    Text(
                        text = date.toCommonDateOnlyExpression(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f, true)
                            .clickable { showDatePicker = true }
                    )

                    // date picker component
                    if (showDatePicker)
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        datePickerState.selectedDateMillis?.let {
                                            date = date.updateDateWithMillis(it)
                                        }
                                        showDatePicker = false
                                    }
                                ) { Text("Confirm") }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker = false
                                    }
                                ) { Text("Cancel") }
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) { DatePicker(state = datePickerState) }

                    if (!switchState) {
                        Text(
                            text = date.toDayTime(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable { showTimePicker = true }
                        )

                        if (showTimePicker) {
                            TimePickerDialog(
                                onDismissRequest = { showTimePicker = false },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            date = date.setTimeInfo(
                                                timePickerState.hour,
                                                timePickerState.minute
                                            )
                                            showTimePicker = false
                                        }
                                    ) { Text("Confirm") }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            showTimePicker = false
                                        }
                                    ) { Text("Cancel") }
                                }
                            )
                            { TimePicker(state = timePickerState) }
                        }
                    }
                }
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // timezone info
        val timezone = TimeZone.getDefault().id
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
                    text = timezone,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // description input
        var description by remember { mutableStateOf("") }
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
                    value = description,
                    onValueChange = { description = it },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    placeholder = {
                        Text(
                            text = "Add description",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    })
            }
        )
    }
}

@Preview
@Composable
fun ScheduleEditScreenPreview() {
    MyCalendarTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) { ScheduleEditScreen() }
    }
}