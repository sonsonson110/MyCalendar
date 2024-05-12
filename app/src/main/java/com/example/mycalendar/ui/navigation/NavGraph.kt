package com.example.mycalendar.ui.navigation

sealed class NavGraph(
    val route: String,
    val navArg: String? = null,
    val routeWithArg: String? = null
) {

    // add graph
    data object ScheduleAddGraph : NavGraph(route = "scheduleAdd") {
        data object ScheduleAdd : NavDestination(route = "${super.route}/add")
        data object LocationSearch : NavDestination(route = "${super.route}/locationSearch")
    }

    // edit graph
    data object ScheduleEditGraph : NavGraph(
        route = "scheduleEdit",
        navArg = "activityId",
        routeWithArg = "scheduleEdit/{activityId}"
    ) {
        data object ScheduleEdit : NavDestination(route = "${super.route}/{${this.navArg}}/edit")
        data object LocationSearch : NavDestination(route = "${super.route}/locationSearch")
    }
//    companion object {
//        const val EDIT_GRAPH_NAV_ARG: String = "activityId"
//    }
}