package com.example.mycalendar.core.data.di

import com.example.mycalendar.core.data.repository.ActivityRepository
import com.example.mycalendar.core.data.repository.ActivityRepositoryImpl
import com.example.mycalendar.core.data.repository.LocationRepository
import com.example.mycalendar.core.data.repository.LocationRepositoryImpl
import com.example.mycalendar.core.data.repository.WeatherRepository
import com.example.mycalendar.core.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsActivityRepository(
        activityRepository: ActivityRepositoryImpl,
    ): ActivityRepository

    @Binds
    abstract fun bindsWeatherRepository(
        weatherRepository: WeatherRepositoryImpl,
    ): WeatherRepository

    @Binds
    abstract fun bindsLocationRepository(
        locationRepository: LocationRepositoryImpl,
    ): LocationRepository
}