package com.example.mycalendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mycalendar.feature.auth.login.LoginScreen
import com.example.mycalendar.feature.auth.signup.SignupScreen
import com.example.mycalendar.feature.schedule.edit.ScheduleAddScreen
import com.example.mycalendar.feature.schedule.edit.ScheduleEditScreen
import com.example.mycalendar.feature.schedule.list.ScheduleScreen

@Composable
fun MyCalendarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
//        startDestination = NavDestination.Login.route,
        startDestination = NavDestination.Schedule.route,
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

        composable(
            route = "${NavDestination.ScheduleEdit().route}/{${NavDestination.ScheduleEdit().navArg}}",
            arguments = listOf(navArgument(NavDestination.ScheduleEdit().navArg) {
                type = NavType.IntType
            })
        ) {
            ScheduleEditScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = NavDestination.Signup.route
        ) {
            SignupScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = NavDestination.ScheduleAdd.route
        ) {
            ScheduleAddScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(
            route = NavDestination.Schedule.route
        ) {
            ScheduleScreen(
                navigateToScheduleEdit = { activityId -> navController.navigate(route = "${NavDestination.ScheduleEdit().route}/$activityId") },
                navigateToScheduleAdd = { navController.navigate(route = NavDestination.ScheduleAdd.route) }
            )
        }
    }
}