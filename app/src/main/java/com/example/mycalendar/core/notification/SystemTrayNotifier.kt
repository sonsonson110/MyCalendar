package com.example.mycalendar.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.mycalendar.R
import com.example.mycalendar.core.data.model.Activity
import com.example.mycalendar.core.data.util.toDayTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val TAG = "SystemTrayNotifier"

interface Notifier {
    fun postActivityRemindNotification(activity: Activity)
}

class SystemTrayNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) : Notifier {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    override fun postActivityRemindNotification(activity: Activity) {

        val notification = createActivityRemindNotification {
            setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("MyCalendar")
                .setContentText("${activity.title ?: "<No title>"} will start soon!")
                .setSubText(activity.startTime!!.toDayTime())
                .setAutoCancel(true)
        }
        notificationManager.notify(activity.id.hashCode(), notification)
    }

    private fun createActivityRemindNotification(
        block: Notification.Builder.() -> Unit,
    ): Notification {
        ensureNotificationChannelExists()
        return Notification.Builder(
            context,
            ACTIVITY_REMIND_NOTIFICATION_CHANNEL_ID,
        )
            .apply(block)
            .build()
    }

    /*
    Make sure the notification channel exists before posting the notification
    */
    private fun ensureNotificationChannelExists() {
        val channel = NotificationChannel(
            ACTIVITY_REMIND_NOTIFICATION_CHANNEL_ID,
            ACTIVITY_REMIND_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "Enable activity remind notifications"
            importance = NotificationManager.IMPORTANCE_DEFAULT
        }
        // register the channel with the system
        notificationManager.createNotificationChannel((channel))
    }
}

private const val ACTIVITY_REMIND_NOTIFICATION_CHANNEL_ID = "activity_remind_notification_channel"
private const val ACTIVITY_REMIND_NOTIFICATION_CHANNEL_NAME = "Activity remind"