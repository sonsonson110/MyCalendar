package com.example.mycalendar.core.database.di

import com.example.mycalendar.core.database.MyCalendarDatabase
import com.example.mycalendar.core.database.dao.EventDao
import com.example.mycalendar.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTaskDao(
        database: MyCalendarDatabase
    ): TaskDao = database.taskDao()

    @Provides
    fun providesEventDao(
        database: MyCalendarDatabase
    ): EventDao = database.eventDao()
}