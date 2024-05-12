package com.example.mycalendar.ui.navigation

sealed class NavGraph(val route: String) {
    data object ScheduleAdd : NavGraph(route = "ScheduleAddGraph")
    data object ScheduleEdit : NavGraph(route = "ScheduleEditGraph")
}