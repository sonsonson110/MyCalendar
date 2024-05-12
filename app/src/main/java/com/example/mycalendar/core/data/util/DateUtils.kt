package com.example.mycalendar.core.data.util

import android.icu.util.Calendar
import org.joda.time.DateTimeComparator
import org.joda.time.DateTimeZone
import org.joda.time.Instant
import java.lang.Long.MAX_VALUE
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.abs
import kotlin.math.min

fun Date.toMillis(timezoneId: String): Long {
    val time = Instant(this.time)
    val asTimezoneZone = DateTimeZone.forID(timezoneId)
    val relativeTime = time.toDateTime(asTimezoneZone)
    return relativeTime.millis
}

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

fun Date.isEqualIgnoreTimeTo(otherDate: Date?): Boolean {
    if (otherDate == null) return false
    return DateTimeComparator.getDateOnlyInstance().compare(this, otherDate) == 0
}

fun Date.toCommonDateOnlyExpression(): String {
    val timeFormat = SimpleDateFormat("EEE, d MMM yyyy")
    return timeFormat.format(this)
}

fun Date.truncateTimeInfo(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@truncateTimeInfo
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }
    return calendar.time
}

fun Date.setTimeInfo(hour: Int, minute: Int): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@setTimeInfo
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }
    return calendar.time
}

fun Date.addByHour(hour: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.HOUR, hour)
    return calendar.time
}

fun Date.updateDateWithMillis(millis: Long): Date {
    val newCalendar = Calendar.getInstance()
    newCalendar.timeInMillis = millis

    val updatedCalendar = Calendar.getInstance().apply {
        time = this@updateDateWithMillis
        set(Calendar.YEAR, newCalendar.get(Calendar.YEAR))
        set(Calendar.MONTH, newCalendar.get(Calendar.MONTH))
        set(Calendar.DAY_OF_MONTH, newCalendar.get(Calendar.DAY_OF_MONTH))
    }
    return updatedCalendar.time
}

fun Int.toMinute(): Int = this / 60
fun Int.toSecond(): Int = this * 60

fun findIndexOfClosestDateFromList(target: Date, dateList: List<Date>): Int {
    var resultDate = dateList.find { date -> date.isEqualIgnoreTimeTo(target) }
    if (resultDate != null)
        return dateList.indexOf(resultDate)

    // Find the closest date from the list
    var minimumDayCount = MAX_VALUE
    dateList.forEach { date ->
        minimumDayCount = min(abs(date.time - target.time), minimumDayCount)
    }
    resultDate = dateList.find { date -> abs(date.time - target.time) == minimumDayCount }
    // this date would be the closest date from minimum
    return dateList.indexOf(resultDate)
}