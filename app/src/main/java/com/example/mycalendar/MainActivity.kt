package com.example.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.mycalendar.core.data.model.NetworkResult
import com.example.mycalendar.core.data.model.Weather
import com.example.mycalendar.core.data.repository.WeatherRepository
import com.example.mycalendar.ui.theme.MyCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                var weather by remember {
                    mutableStateOf<NetworkResult<Weather>>(NetworkResult.Loading())
                }

                LaunchedEffect(key1 = Unit) {
                    weatherRepository.getCurrentWeather(lon = 20.0, lat = 100.0)
                        .catch { e ->
                            weather = NetworkResult.Error(message = e.toString())
                        }
                        .collect { data ->
                            weather = NetworkResult.Success(data = data)
                        }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        when (weather) {
                            is NetworkResult.Loading -> LinearProgressIndicator()
                            is NetworkResult.Error -> Text(text = weather.message.toString(), fontSize = 7.sp)
                            else -> Text(text = weather.data.toString(), fontSize = 7.sp)
                        }
                    }
                }
            }
        }
    }
}