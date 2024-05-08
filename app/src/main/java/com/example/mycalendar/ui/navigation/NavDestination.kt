package com.example.mycalendar.ui.navigation

sealed class NavDestination(open val route: String) {
    data object Login : NavDestination(route = "login")
    data object Signup : NavDestination(route = "signup")
    data object Schedule : NavDestination(route = "schedule")
    data object ScheduleAdd : NavDestination(route = "scheduleAdd")
    data class ScheduleEdit(
        val navArg: String = "activityId",
        override val route: String = "scheduleEdit"
    ) : NavDestination(route)

    data object LocationSearch : NavDestination(route = "locationSearch")
}