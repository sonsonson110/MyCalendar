package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Weather
import com.example.mycalendar.core.network.OpenWeatherMapNetwork
import com.example.mycalendar.core.network.model.toWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherMapNetwork: OpenWeatherMapNetwork,
): WeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): Flow<Weather> = flow {
        val response = openWeatherMapNetwork.getCurrentWeather(lon, lat).toWeather()
        emit(response)
    }.flowOn(Dispatchers.IO)
}