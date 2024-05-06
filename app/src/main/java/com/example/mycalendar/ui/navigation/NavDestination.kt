package com.example.mycalendar.ui.navigation

sealed class NavDestination(val route: String) {
    data object Login: NavDestination(route = "login")
    data object Signup: NavDestination(route = "signup")
    data object Schedule: NavDestination(route = "schedule")

    data object ScheduleEdit: NavDestination(route = "scheduleEdit")
}