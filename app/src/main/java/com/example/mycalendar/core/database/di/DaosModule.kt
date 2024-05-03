package com.example.mycalendar.core.database.di

import com.example.mycalendar.core.database.MyCalendarDatabase
import com.example.mycalendar.core.database.dao.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesActivityDao(
        database: MyCalendarDatabase
    ): ActivityDao = database.activityDao()
}