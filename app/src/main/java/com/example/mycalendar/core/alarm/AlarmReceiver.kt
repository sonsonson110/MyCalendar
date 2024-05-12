package com.example.mycalendar.core.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.notification.Notifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private const val TAG = "AlarmReceiver"

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notifier: Notifier
    @Inject
    lateinit var activityRepository: ActivityRepository

    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        val activityId: Int? = intent?.getIntExtra("activityId", -1)
        if (activityId == null || activityId == -1)
            return@goAsync

        val activity = activityRepository.getLocalActivityDetailById(activityId)
        notifier.postActivityRemindNotification(activity)
        Log.d(TAG, "onReceive: $activity --- DONE")
    }

    /*
        Run task in coroutine scope so that `onReceive` knows task is still running
        Must complete within 10 seconds
    */
    @OptIn(DelicateCoroutinesApi::class)
    private fun goAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val pendingResult = goAsync()
        GlobalScope.launch(context) {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }
}