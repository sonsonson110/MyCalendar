package com.example.mycalendar.core.data.util

import org.joda.time.DateTimeComparator
import java.lang.Long.MAX_VALUE
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.abs
import kotlin.math.min

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

fun Date.toMonthName(): String {
    val timeFormat = SimpleDateFormat("MMMM")
    return timeFormat.format(this)
}

fun Date.isEqualIgnoreTimeTo(otherDate: Date): Boolean {
    return DateTimeComparator.getDateOnlyInstance().compare(this, otherDate) == 0
}

fun Date.toCommonDateOnlyExpression(): String {
    val timeFormat = SimpleDateFormat("EEE, d MMM yyyy")
    return timeFormat.format(this)
}

fun findIndexOfClosestDateFromList(target: Date, dateList: List<Date>): Int {
    // Find the minimum amount of days to the current date
    var minimumDayCount = MAX_VALUE

    dateList.forEach { date ->
        minimumDayCount = min(abs(date.time - target.time), minimumDayCount)
    }
    // Get the first one that match the time gap
    val resultDate = dateList.find { date ->  abs(date.time - target.time) == minimumDayCount }
    // this date would match or be closest date from minimum
    return dateList.indexOf(resultDate)
}