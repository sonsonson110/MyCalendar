package com.example.mycalendar.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.mycalendar.core.database.MyCalendarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesMyCalendarDatabase(
        @ApplicationContext context: Context,
    ): MyCalendarDatabase = Room.databaseBuilder(
        context,
        MyCalendarDatabase::class.java,
        "app-db4.db",
    )
        .build()
}