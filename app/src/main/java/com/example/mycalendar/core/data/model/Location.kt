package com.example.mycalendar.core.data.model

import com.example.mycalendar.core.database.model.LocationEntity

data class Location(
    val placeId: Long? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val displayName: String? = null,
)

fun Location.toLocationEntity() = LocationEntity(
    placeId = this.placeId!!,
    lon = this.lon!!,
    lat = this.lat!!,
    displayName = this.displayName!!,
)

fun Location.toLocationHashMap() = hashMapOf(
    "placeId" to this.placeId!!,
    "lon" to this.lon!!,
    "lat" to this.lat!!,
    "displayName" to this.displayName!!,
)
