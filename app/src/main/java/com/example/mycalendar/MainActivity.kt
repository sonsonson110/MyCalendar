package com.example.mycalendar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.mycalendar.core.data.model.Event
import com.example.mycalendar.core.data.model.Task
import com.example.mycalendar.core.data.repository.EventRepository
import com.example.mycalendar.core.data.repository.TaskRepository
import com.example.mycalendar.ui.theme.MyCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var eventRepository: EventRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                var task by remember {
                    mutableStateOf(emptyList<Task>())
                }
                var event by remember {
                    mutableStateOf(Event())
                }

                LaunchedEffect(key1 = Unit) {
                    val data0 = taskRepository.getAllTasks()
                    task = data0


                    val data = eventRepository.getEventById(4)
                    Log.d(TAG, data.toString())
                    event = data
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(text = task.toString(), fontSize = 7.sp)
                        Text(text = event.toString(), fontSize = 7.sp)
                    }
                }
            }
        }
    }
}