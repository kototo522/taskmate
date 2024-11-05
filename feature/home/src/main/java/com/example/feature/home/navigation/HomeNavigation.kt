package com.example.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.model.navigation.HomeNavigation
import com.example.core.model.navigation.HomeNavigation.Companion.HOME_GRAPH_ROUTE
import com.example.core.model.navigation.HomeNavigation.Companion.SUBJECT_LIST_ROUTE
import com.example.feature.home.HomeScreen
import com.example.feature.home.components.SubjectListScreen

fun NavGraphBuilder.homeNavGraph(
    homeController: HomeNavigation,
    user: TaskMateUser?,
) {
    val popBackStack: () -> Unit = { homeController.popBackStack() }
    val navToSettingScreen = homeController.navToSettingScreen
    val navToSubjectListScreen = homeController.navToSubjectListScreen

    navigation(
        startDestination = BottomNavBarItems.Home.route,
        route = HOME_GRAPH_ROUTE,
    ) {
        composable(route = BottomNavBarItems.Home.route) {
            HomeScreen(navToSettingScreen, navToSubjectListScreen)
        }
        composable(route = SUBJECT_LIST_ROUTE) {
            SubjectListScreen(className = "", popBackStack)
        }
    }
}
