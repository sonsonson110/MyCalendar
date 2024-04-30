package com.example.mycalendar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.mycalendar.core.database.dao.EventDao
import com.example.mycalendar.core.database.dao.TaskDao
import com.example.mycalendar.core.database.model.EventEntity
import com.example.mycalendar.core.database.model.LocationEntity
import com.example.mycalendar.core.database.model.TaskEntity
import com.example.mycalendar.core.database.model.EventAndTaskAndLocationAndParticipants
import com.example.mycalendar.core.database.model.TaskAndUser
import com.example.mycalendar.core.database.model.UserEntity
import com.example.mycalendar.core.database.model.toEvent
import com.example.mycalendar.ui.theme.MyCalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    @Inject lateinit var taskDao: TaskDao
    @Inject lateinit var eventDao: EventDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCalendarTheme {
                var taskAndUser by remember {
                    mutableStateOf(emptyList<TaskAndUser>())
                }
                var eventAndTaskAndLocationAndParticipants by remember {
                    mutableStateOf(EventAndTaskAndLocationAndParticipants(
                        EventEntity(0,Date(),null,null,null),
//                        TaskEntity(0,null,null, Date(), "","",null,false, ""),
                        TaskAndUser(
                            TaskEntity(0,null,null, Date(), "","",null,false, ""),
                            UserEntity("","","",false)
                        ),
                        LocationEntity(0,0.0,0.0,""),
                        emptyList()

                    ))
                }

                LaunchedEffect(key1 = Unit) {
                    val data0 = taskDao.getAllTasks()
                    taskAndUser = data0


                    val data = eventDao.getEventById(4)
                    Log.d(TAG, data.toString())
                    eventAndTaskAndLocationAndParticipants = data
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text(text = eventAndTaskAndLocationAndParticipants.toEvent().toString(), fontSize = 8.sp)
                }
            }
        }
    }
}