package com.example.mycalendar.core.data.di

import com.example.mycalendar.core.data.repository.EventRepository
import com.example.mycalendar.core.data.repository.EventRepositoryImpl
import com.example.mycalendar.core.data.repository.TaskRepository
import com.example.mycalendar.core.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsTaskRepository(
        taskRepository: TaskRepositoryImpl,
    ): TaskRepository

    @Binds
    abstract fun bindsEventRepository(
        eventRepository: EventRepositoryImpl,
    ): EventRepository
}