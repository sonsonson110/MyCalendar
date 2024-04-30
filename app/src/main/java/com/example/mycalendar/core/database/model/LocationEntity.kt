package com.example.mycalendar.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycalendar.core.data.model.Location

@Entity(tableName = "Location")
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Int,
    val lon: Double,
    val lat: Double,
    @ColumnInfo(name = "display_name") val displayName: String,
)

fun LocationEntity.toLocation() = Location(
    placeId = this.placeId,
    lon = this.lon,
    lat = this.lat,
    displayName = this.displayName,
)
