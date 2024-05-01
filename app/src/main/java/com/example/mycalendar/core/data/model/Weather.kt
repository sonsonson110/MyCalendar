package com.example.mycalendar.core.data.model


data class Weather(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null,

    var temp: Double? = null,
    var feelsLike: Double? = null,
    var humidity: Int? = null,
) {
    val iconUrl: String?
        get() = if (icon == null) null else "https://openweathermap.org/img/wn/$icon.png"
}