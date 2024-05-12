package com.example.mycalendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.mycalendar.feature.auth.login.LoginScreen
import com.example.mycalendar.feature.auth.signup.SignupScreen
import com.example.mycalendar.feature.schedule.edit.ScheduleAddScreen
import com.example.mycalendar.feature.schedule.edit.ScheduleAddViewModel
import com.example.mycalendar.feature.schedule.edit.ScheduleEditScreen
import com.example.mycalendar.feature.schedule.edit.ScheduleEditViewModel
import com.example.mycalendar.feature.schedule.list.ScheduleScreen
import com.example.mycalendar.feature.search.LocationSearchScreen

@Composable
fun MyCalendarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
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
            route = NavDestination.Signup.route
        ) {
            SignupScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(
            route = NavDestination.Schedule.route
        ) {
            ScheduleScreen(
                navigateToScheduleEdit = { activityId ->
                    val graph = NavGraph.ScheduleEditGraph
                    navController.navigate(route = "${graph.route}/$activityId")
                },
                navigateToScheduleAdd = { navController.navigate(route = NavGraph.ScheduleAddGraph.route) },
            )
        }

        navigation(
            startDestination = NavGraph.ScheduleAddGraph.ScheduleAdd.route,
            route = NavGraph.ScheduleAddGraph.route // route id for navigation graph
        ) {
            composable(
                route = NavGraph.ScheduleAddGraph.ScheduleAdd.route
            ) { backStackEntry ->
                // MUST NOT forget to create viewModel manually when define `composable` inside a `navigation`
                val scheduleAddViewModel =
                    backStackEntry.sharedViewModel<ScheduleAddViewModel>(navController = navController)
                ScheduleAddScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToLocationPick = { navController.navigate(NavGraph.ScheduleAddGraph.LocationSearch.route) },
                    viewModel = scheduleAddViewModel,
                )
            }

            composable(
                route = NavGraph.ScheduleAddGraph.LocationSearch.route
            ) { backStackEntry ->
                // get the viewModel from parent
                val scheduleAddViewModel =
                    backStackEntry.sharedViewModel<ScheduleAddViewModel>(navController = navController)
                val query by scheduleAddViewModel.searchQuery.collectAsState()
                val locationSearchUiState by scheduleAddViewModel.locationSearchUiState.collectAsState()
                LocationSearchScreen(
                    onNavigateBack = { navController.popBackStack() },
                    query = query,
                    onQueryChange = scheduleAddViewModel::onSearchQueryChanged,
                    locationSearchUiState = locationSearchUiState,
                    onLocationSelected = scheduleAddViewModel::onLocationSelected,
                )
            }
        }

        navigation(
            startDestination = NavGraph.ScheduleEditGraph.ScheduleEdit.route,
            route = NavGraph.ScheduleEditGraph.routeWithArg!!,
            arguments = listOf(navArgument(NavGraph.ScheduleEditGraph.navArg!!) { type = NavType.IntType })
        ) {
            composable(
                route = NavGraph.ScheduleEditGraph.ScheduleEdit.route,
                arguments = listOf(navArgument(NavGraph.ScheduleEditGraph.navArg) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val scheduleEditViewModel =
                    backStackEntry.sharedViewModel<ScheduleEditViewModel>(navController = navController)
                ScheduleEditScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToLocationPick = { navController.navigate(NavGraph.ScheduleEditGraph.LocationSearch.route) },
                    viewModel = scheduleEditViewModel,
                )
            }

            composable(
                route = NavGraph.ScheduleEditGraph.LocationSearch.route
            ) { backStackEntry ->
                // get the viewModel from parent
                val scheduleEditViewModel =
                    backStackEntry.sharedViewModel<ScheduleEditViewModel>(navController = navController)
                val query by scheduleEditViewModel.searchQuery.collectAsState()
                val locationSearchUiState by scheduleEditViewModel.locationSearchUiState.collectAsState()
                LocationSearchScreen(
                    onNavigateBack = { navController.popBackStack() },
                    query = query,
                    onQueryChange = scheduleEditViewModel::onSearchQueryChanged,
                    locationSearchUiState = locationSearchUiState,
                    onLocationSelected = scheduleEditViewModel::onLocationSelected,
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val parentNavGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentNavGraphRoute)
    }
    return hiltViewModel(parentEntry)
}