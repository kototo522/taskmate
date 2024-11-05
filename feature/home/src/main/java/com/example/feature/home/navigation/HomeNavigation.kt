package com.example.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.model.navigation.HomeNavigation
import com.example.core.model.navigation.HomeNavigation.Companion.HOME_GRAPH_ROUTE
import com.example.core.model.navigation.HomeNavigation.Companion.SELECT_GROUP_ROUTE
import com.example.core.model.navigation.HomeNavigation.Companion.SUBJECT_LIST_ROUTE
import com.example.feature.home.HomeScreen
import com.example.feature.home.components.SelectGroupScreen
import com.example.feature.home.components.SubjectListScreen

fun NavGraphBuilder.homeNavGraph(
    homeController: HomeNavigation,
    user: TaskMateUser?,
) {
    val popBackStack: () -> Unit = { homeController.popBackStack() }
    val navToSettingScreen = homeController.navToSettingScreen
    val navToSubjectListScreen = homeController.navToSubjectListScreen
    val navToSelectGroupScreen = homeController.navToSelectGroupScreen

    navigation(
        startDestination = BottomNavBarItems.Home.route,
        route = HOME_GRAPH_ROUTE,
    ) {
        composable(route = BottomNavBarItems.Home.route) {
            HomeScreen(navToSettingScreen, navToSubjectListScreen)
        }

        composable("$SUBJECT_LIST_ROUTE/{clickedClass}/{dayTime}") { backStackEntry ->
            val clickedClass = backStackEntry.arguments?.getString("clickedClass")
            val dayTime = backStackEntry.arguments?.getString("dayTime")
            SubjectListScreen(dayTime = dayTime, navToSelectGroupScreen, popBackStack)
        }

        composable(route = SELECT_GROUP_ROUTE) {
            SelectGroupScreen(popBackStack)
        }
    }
}
