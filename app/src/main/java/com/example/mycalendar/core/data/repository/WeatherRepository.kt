package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): Flow<Weather>
}