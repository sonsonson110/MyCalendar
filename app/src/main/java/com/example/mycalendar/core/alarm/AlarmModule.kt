package com.example.mycalendar.core.alarm

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {
    @Binds
    abstract fun bindAlarmScheduler(alarmScheduler: MyCalendarAlarmScheduler): AlarmScheduler
}