package com.example.mycalendar.core.network.model

import com.example.mycalendar.core.data.model.Location
import com.google.gson.annotations.SerializedName

data class NetworkLocation(
    @SerializedName("place_id") var placeId: Long? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("display_name") var displayName: String? = null,
)

fun NetworkLocation.toLocation() = Location(
    placeId = this.placeId,
    lon = this.lon,
    lat = this.lat,
    displayName = this.displayName,
)
