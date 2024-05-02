package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.network.LocationIqNetwork
import com.example.mycalendar.core.network.model.NetworkLocation
import com.example.mycalendar.core.network.model.toLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface LocationRepository {
    suspend fun getCurrentLocationNameFromNetwork(lat: Double, lon: Double): Flow<Location>
    suspend fun getAutocompleteLocationsFromNetwork(query: String): Flow<List<Location>>
}

class LocationRepositoryImpl @Inject constructor(
    private val locationIqNetwork: LocationIqNetwork,
): LocationRepository {
    override suspend fun getCurrentLocationNameFromNetwork(lat: Double, lon: Double): Flow<Location> = flow {
        val response = locationIqNetwork.getCurrentLocationName(lat = lat, lon = lon)
        emit(response.toLocation())
    }.flowOn(Dispatchers.IO)

    override suspend fun getAutocompleteLocationsFromNetwork(query: String): Flow<List<Location>> = flow {
        val response = locationIqNetwork.getAutocompleteLocations(query = query)
        emit(response.map(NetworkLocation::toLocation))
    }.flowOn(Dispatchers.IO)

}