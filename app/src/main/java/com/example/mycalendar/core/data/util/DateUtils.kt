package com.example.mycalendar.core.data.util

import java.text.SimpleDateFormat
import java.util.Date

//
//fun Date.toStringDate(): String {
//    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//    return dateFormat.format(this)
//}
//
//fun String.toDate(): Date {
//    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
//    return dateFormat.parse(this)
//}
//
//fun Date.toStringTime(): String {
//    val timeFormat = SimpleDateFormat("HH:mm")
//    return timeFormat.format(this)
//}
//
//fun String.toTimeOfDate(): Date {
//    val timeFormat = SimpleDateFormat("HH:mm")
//    return timeFormat.parse(this)
//}

fun Date.toDayNameInWeek(): String {
    val timeFormat = SimpleDateFormat("EEE")
    return timeFormat.format(this)
}

fun Date.toDayInMonth(): String {
    val timeFormat = SimpleDateFormat("d")
    return timeFormat.format(this)
}

fun Date.toDayTime(): String {
    val timeFormat = SimpleDateFormat("h:mm a")
    return timeFormat.format(this)
}