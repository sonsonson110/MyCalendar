package com.example.mycalendar.core.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


fun Date.toStringDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(this)
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.parse(this)
}

fun Date.toStringTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm")
    return timeFormat.format(this)
}

fun String.toTimeOfDate(): Date {
    val timeFormat = SimpleDateFormat("HH:mm")
    return timeFormat.parse(this)
}

fun Date.addFromTime(time: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.HOUR, time.hours)
    calendar.add(Calendar.MINUTE, time.minutes)
    return calendar.time
}

fun Date.toDayNameInWeek(): String {
    val timeFormat = SimpleDateFormat("EEE")
    return timeFormat.format(this)
}

fun Date.toDayInMonth(): String {
    val timeFormat = SimpleDateFormat("d")
    return timeFormat.format(this)
}