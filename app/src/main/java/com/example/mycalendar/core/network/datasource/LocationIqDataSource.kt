package com.example.mycalendar.core.network.datasource

import com.example.mycalendar.core.network.model.NetworkLocation

interface LocationIqDataSource {
    suspend fun getCurrentLocationName(lat: Double, lon: Double): NetworkLocation

    suspend fun getAutocompleteLocations(query: String): List<NetworkLocation>
}