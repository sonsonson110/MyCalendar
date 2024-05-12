package com.example.mycalendar.core.alarm

import com.example.mycalendar.core.data.model.Activity

interface AlarmScheduler {
    fun schedule(activity: Activity)
    fun cancel(activity: Activity)
}