package com.example.mycalendar.core.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.util.toMillis
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val TAG = "MyCalendarAlarmScheduler"
class MyCalendarAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun schedule(activity: Activity) {
        if (activity.reminderOffsetSeconds == null)
            return

        // get the relative time base on the default start time and time zone
        val relativeTime = activity.startTime!!.toMillis(activity.timeZone!!)
        // the time to set the alarm
        val alarmMillis = relativeTime + activity.reminderOffsetSeconds * 1000 // to Millis
        // send the activityId with the intent
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("activityId", activity.id)
        }

        if (alarmManager.canScheduleExactAlarms())
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmMillis,
                PendingIntent.getBroadcast(
                    context, activity.id, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            )
    }

    override fun cancel(activity: Activity) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context, activity.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        )
    }
}