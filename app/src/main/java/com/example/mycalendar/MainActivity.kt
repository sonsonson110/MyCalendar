package com.example.mycalendar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mycalendar.core.data.repository.AuthRepository
import com.example.mycalendar.ui.navigation.MyCalendarNavHost
import com.example.mycalendar.ui.navigation.NavDestination
import com.example.mycalendar.ui.theme.MyCalendarTheme
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var authRepository: AuthRepository
    private lateinit var navController: NavHostController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // check if user had logged in when start up
            LaunchedEffect(key1 = Unit) {
                val user: FirebaseUser? = authRepository.getCurrentAuthUser()
                if (user == null) {
                    navController.navigate(NavDestination.Login.route) {
                        // remove the home destination
                        popUpTo(NavDestination.Schedule.route) {
                            inclusive = true
                        }
                    }
                }
            }

            MyCalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    MyCalendarNavHost(navController = navController)
                }
            }
        }
    }
}