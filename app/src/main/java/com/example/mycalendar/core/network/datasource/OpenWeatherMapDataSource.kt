package com.example.mycalendar.core.network.datasource

import androidx.core.os.trace
import com.example.mycalendar.core.network.model.NetworkWeather
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

interface OpenWeatherMapDataSource {
    suspend fun getCurrentWeather(lon: Double, lat: Double):  NetworkWeather
}
