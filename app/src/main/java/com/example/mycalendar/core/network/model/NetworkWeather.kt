package com.example.mycalendar.core.network.model

import com.example.mycalendar.core.data.model.Weather
import com.google.gson.annotations.SerializedName

data class NetworkWeather(
    @SerializedName("weather") var weather: ArrayList<NetworkWeatherCondition> = arrayListOf(),
    @SerializedName("main") var main: NetworkWeatherMain? = NetworkWeatherMain()
)

data class NetworkWeatherCondition(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
)

data class NetworkWeatherMain(
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("humidity") var humidity: Int? = null,
)

fun NetworkWeather.toWeather(): Weather {
    return Weather(
        id = this.weather[0].id,
        main = this.weather[0].main,
        description  = this.weather[0].description,
        icon = this.weather[0].icon,
        temp = this.main?.temp,
        feelsLike = this.main?.feelsLike,
        humidity = this.main?.humidity,
    )
}