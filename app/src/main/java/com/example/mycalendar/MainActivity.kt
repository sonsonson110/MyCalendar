package com.example.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.mycalendar.core.data.model.Location
import com.example.mycalendar.core.data.model.NetworkResult
import com.example.mycalendar.core.data.repository.LocationRepository
import com.example.mycalendar.ui.theme.MyCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var locationRepository: LocationRepository

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {

                var currentLocation by remember {
                    mutableStateOf<NetworkResult<Location>>(NetworkResult.Loading())
                }

                var autocompleteLocations by remember {
                    mutableStateOf<NetworkResult<List<Location>>>(NetworkResult.Empty())
                }

                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(key1 = Unit) {
                    locationRepository.getCurrentLocationNameFromNetwork(lat = 20.0, lon = 100.0)
                        .catch { e ->
                            currentLocation = NetworkResult.Error(message = e.toString())
                        }
                        .collect { data ->
                            currentLocation = NetworkResult.Success(data = data)
                        }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        when(currentLocation) {
                            is NetworkResult.Loading -> LinearProgressIndicator()
                            is NetworkResult.Error -> Text(text = currentLocation.message!!)
                            else -> Text(text = currentLocation.data.toString())
                        }
                        var query by remember { mutableStateOf("") }
                        TextField(value = query, onValueChange = { query = it })
                        Button(onClick = {
                            coroutineScope.launch {
                                if (query.length <= 3) return@launch
                                autocompleteLocations = NetworkResult.Loading()
                                locationRepository.getAutocompleteLocationsFromNetwork(query)
                                    .catch { e ->
                                        autocompleteLocations = NetworkResult.Error(message = e.toString())
                                    }
                                    .collect { data ->
                                        autocompleteLocations = if (data.isEmpty())
                                            NetworkResult.Empty()
                                        else
                                            NetworkResult.Success(data = data)
                                    }
                            }
                        }) {
                            Text("Search")
                        }
                        when(autocompleteLocations) {
                            is NetworkResult.Empty -> Text("Field empty")
                            is NetworkResult.Error -> Text(autocompleteLocations.message!!)
                            is NetworkResult.Loading -> LinearProgressIndicator()
                            is NetworkResult.Success -> Text(autocompleteLocations.data.toString())
                        }
                    }
                }
            }
        }
    }
}