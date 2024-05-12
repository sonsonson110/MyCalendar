package com.example.mycalendar.core.data.repository

import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.model.toLocationEntity
import com.example.mycalendar.core.database.dao.LocationDao
import com.example.mycalendar.core.network.LocationIqNetwork
import com.example.mycalendar.core.network.model.NetworkLocation
import com.example.mycalendar.core.network.model.toLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocationRepository {
    fun getCurrentLocationNameFromNetwork(lat: Double, lon: Double): Flow<Location>
    fun getAutocompleteLocationsFromNetwork(query: String): Flow<List<Location>>
    suspend fun addLocalLocation(location: Location)
}

class LocationRepositoryImpl @Inject constructor(
    private val locationIqNetwork: LocationIqNetwork,
    private val locationDao: LocationDao,
): LocationRepository {
    override fun getCurrentLocationNameFromNetwork(lat: Double, lon: Double): Flow<Location> = flow {
        val response = locationIqNetwork.getCurrentLocationName(lat = lat, lon = lon)
        emit(response.toLocation())
    }

    override fun getAutocompleteLocationsFromNetwork(query: String): Flow<List<Location>> = flow {
        val response = locationIqNetwork.getAutocompleteLocations(query = query)
        emit(response.map(NetworkLocation::toLocation))
    }

    override suspend fun addLocalLocation(location: Location) {
        locationDao.insert(location.toLocationEntity())
    }
}