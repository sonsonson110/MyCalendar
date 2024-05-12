package com.example.mycalendar.core.network

import com.example.mycalendar.BuildConfig
import com.example.mycalendar.core.network.datasource.LocationIqDataSource
import com.example.mycalendar.core.network.model.NetworkLocation
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
    Retrofit API declaration for LocationIq
*/
private const val BASE_URL = "https://api.locationiq.com/v1/"
private const val API_KEY = BuildConfig.LOCATIONIQ_API_KEY
private interface RetrofitLocationIqApi {
    @GET(value = "reverse?format=json")
    suspend fun getCurrentLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("key") key: String,
    ): NetworkLocation

    @GET(value="autocomplete?limit=4")
    suspend fun getAutocompleteLocations(
        @Query("q") q: String,
        @Query("key") key: String,
    ): List<NetworkLocation>
}

class LocationIqNetwork(
    gson: Gson,
    okHttpClient: OkHttpClient,
): LocationIqDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(RetrofitLocationIqApi::class.java)
    override suspend fun getCurrentLocationName(lat: Double, lon: Double): NetworkLocation {
        return retrofit.getCurrentLocation(lat = lat, lon = lon, key = API_KEY)
    }

    override suspend fun getAutocompleteLocations(query: String): List<NetworkLocation> {
        return retrofit.getAutocompleteLocations(q = query, key = API_KEY)
    }

}