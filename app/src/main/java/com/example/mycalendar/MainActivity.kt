package com.example.mycalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.mycalendar.core.data.model.Task
import com.example.mycalendar.core.data.model.User
import com.example.mycalendar.core.data.repository.TaskRepository
import com.example.mycalendar.core.data.util.addFromTime
import com.example.mycalendar.core.data.util.toDate
import com.example.mycalendar.core.data.util.toStringDate
import com.example.mycalendar.core.data.util.toStringTime
import com.example.mycalendar.core.data.util.toTimeOfDate
import com.example.mycalendar.ui.theme.MyCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.TimeZone
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var task by remember { mutableStateOf(Task()) }
            var tasksCount by remember { mutableIntStateOf(0) }
            val coroutineScope = rememberCoroutineScope()

            MyCalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = Unit) {
                        task = taskRepository.getTaskById(3)
                        tasksCount = taskRepository.getAllTasks().size
                    }

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(text = task.toString())
                        TextField(
                            value = task.title ?: "",
                            onValueChange = { task = task.copy(title = it) },
                            label = { Text("title") })
                        TextField(
                            value = task.description ?: "",
                            onValueChange = { task = task.copy(description = it) },
                            label = { Text("description") })
                        TextField(
                            value = task.startTime?.toStringDate() ?: "",
                            onValueChange = { task = task.copy(startTime = it.toDate()) },
                            label = { Text("start date") })
                        TextField(
                            value = task.startTime?.toStringTime() ?: "",
                            onValueChange = {
                                val time = it.toTimeOfDate()
                                val newDate = task.startTime?.addFromTime(time)
                                task = task.copy(startTime = newDate)
                            },
                            label = { Text("start time") })

                        task = task.copy(
                            type = "task",
                            timeZone = TimeZone.getDefault().id,
                            createdUser = User(uid = "QcxuuWmIipV9iD2jDhmYdsDPhxs2")
                        )

                        TextField(
                            value = task.type!!,
                            onValueChange = {},
                            enabled = false,
                            label = { Text("type") })
                        TextField(
                            value = task.timeZone!!,
                            onValueChange = {},
                            enabled = false,
                            label = { Text("type") })
                        TextField(
                            value = task.reminderOffsetSeconds?.toString() ?: "0",
                            onValueChange = {
                                task = task.copy(reminderOffsetSeconds = it.toInt())
                            },
                            label = { Text("reminderOffsetSeconds") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Button(onClick = {
                            coroutineScope.launch {
//                                taskRepository.addTask(task)
                                taskRepository.updateTask(task)
                                tasksCount = taskRepository.getAllTasks().size
                            }
                        }) {
//                            Text(text = "ADD TO DATABASE")
                            Text(text = "UPDATE TO DATABASE")
                        }
                        Text(text = tasksCount.toString())
                        Button(onClick = {
                            coroutineScope.launch {
                                taskRepository.deleteTask(task)
                                tasksCount = taskRepository.getAllTasks().size
                            }
                        }) {
                            Text(text = "DELETE ${task.id} FROM DATABASE")
                        }
                    }
                }
            }
        }
    }
}