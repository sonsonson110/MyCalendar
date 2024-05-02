package com.example.mycalendar.core.network.di

import com.example.mycalendar.core.network.LocationIqNetwork
import com.example.mycalendar.core.network.OpenWeatherMapNetwork
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesOpenWeatherMapNetwork(gson: Gson, okHttpClient: OkHttpClient): OpenWeatherMapNetwork {
        return OpenWeatherMapNetwork(gson, okHttpClient)
    }

    @Provides
    @Singleton
    fun providesLocationIqNetwork(gson: Gson, okHttpClient: OkHttpClient): LocationIqNetwork {
        return LocationIqNetwork(gson, okHttpClient)
    }
}