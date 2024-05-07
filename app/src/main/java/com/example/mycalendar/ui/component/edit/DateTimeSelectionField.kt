package com.example.mycalendar.ui.component.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.mycalendar.core.data.util.toCommonDateOnlyExpression
import com.example.mycalendar.core.data.util.toDayTime
import com.example.mycalendar.ui.component.ScheduleDetailFieldTemplate
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelectionField(
    date: Date,
    onDatePicked: (Long) -> Unit,
    onTimePicked: (Int, Int) -> Unit,
    isAllDay: Boolean,
    gutterWidth: Dp,
) {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    var timePickerState by remember { mutableStateOf(TimePickerState(
        initialHour = date.hours,
        initialMinute = date.minutes,
        is24Hour = false
    )) }

    LaunchedEffect(key1 = date) {
        timePickerState = TimePickerState(
            initialHour = date.hours,
            initialMinute = date.minutes,
            is24Hour = false
        )
    }

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
                                        onDatePicked(it)
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
                    ) { DatePicker(state = datePickerState) }

                if (!isAllDay) {
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
                                        onTimePicked(timePickerState.hour, timePickerState.minute)
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
}