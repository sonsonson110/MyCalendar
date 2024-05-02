package com.example.mycalendar.core.network

import com.example.mycalendar.BuildConfig
import com.example.mycalendar.core.network.datasource.OpenWeatherMapDataSource
import com.example.mycalendar.core.network.model.NetworkWeather
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton


private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = BuildConfig.OPENWEATHERMAP_API_KEY

/*
    Retrofit API declaration for OpenWeatherMap
 */
private interface RetrofitOpenWeatherMapApi {
    @GET(value = "weather?units=metric")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") appId: String,
    ): NetworkWeather
}


@Singleton
class OpenWeatherMapNetwork @Inject constructor(
    gson: Gson,
    okHttpClient: OkHttpClient,
) : OpenWeatherMapDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(RetrofitOpenWeatherMapApi::class.java)

    override suspend fun getCurrentWeather(
        lon: Double,
        lat: Double,
    ): NetworkWeather = retrofit.getCurrentWeather(lon, lat, API_KEY)
}