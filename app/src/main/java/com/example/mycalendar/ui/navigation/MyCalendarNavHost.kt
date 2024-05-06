package com.example.mycalendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycalendar.feature.auth.login.LoginScreen
import com.example.mycalendar.feature.auth.signup.SignupScreen
import com.example.mycalendar.feature.schedule.ScheduleEditScreen
import com.example.mycalendar.feature.schedule.ScheduleScreen

@Composable
fun MyCalendarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
//        startDestination = NavDestination.Login.route,
        startDestination = NavDestination.ScheduleEdit.route,
    ) {
        composable(
            route = NavDestination.Login.route
        ) {
            LoginScreen(
                navigateToSignUpScreen = { navController.navigate(route = NavDestination.Signup.route) },
                navigateToScheduleScreen = {
                    navController.navigate(route = NavDestination.Schedule.route) {
                        // remove login screen from back stack before navigate to schedule screen
                        popUpTo(NavDestination.Login.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(route = NavDestination.ScheduleEdit.route) {
            ScheduleEditScreen()
        }

        composable(
            route = NavDestination.Signup.route
        ) {
            SignupScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = NavDestination.Schedule.route
        ) {
            ScheduleScreen(
                navigateToScheduleEdit = { navController.navigate(route = NavDestination.ScheduleEdit.route) },
            )
        }
    }
}