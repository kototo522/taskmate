package com.example.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.model.navigation.HomeNavigation
import com.example.core.model.navigation.HomeNavigation.Companion.HOME_GRAPH_ROUTE
import com.example.core.model.navigation.HomeNavigation.Companion.SELECT_GROUP_ROUTE
import com.example.core.model.navigation.HomeNavigation.Companion.SUBJECT_LIST_ROUTE
import com.example.feature.home.HomeScreen
import com.example.feature.home.SelectGroupScreen
import com.example.feature.home.SubjectListScreen

fun NavGraphBuilder.homeNavGraph(
    homeController: HomeNavigation,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
) {
    val popBackStack: () -> Unit = { homeController.popBackStack() }
    val navToHomeScreen = homeController.navToHomeScreen
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

        composable("$SUBJECT_LIST_ROUTE/{clickedClass}/{rowIndex}/{columnIndex}") { backStackEntry ->
            val rowIndex = backStackEntry.arguments?.getString("rowIndex")?.toIntOrNull() ?: 0
            val columnIndex = backStackEntry.arguments?.getString("columnIndex")?.toIntOrNull() ?: 0
            val clickedClass = backStackEntry.arguments?.getString("clickedClass")
            val dayTime = backStackEntry.arguments?.getString("dayTime")

            SubjectListScreen(clickedClass, rowIndex, columnIndex, navToSelectGroupScreen, popBackStack)
        }

        composable(route = "$SELECT_GROUP_ROUTE/{rowIndex}/{columnIndex}") { backStackEntry ->
            val rowIndex = backStackEntry.arguments?.getString("rowIndex")?.toIntOrNull() ?: 0
            val columnIndex = backStackEntry.arguments?.getString("columnIndex")?.toIntOrNull() ?: 0
            val onRegisterClick: (String, String) -> Unit = { subjectName, selectedGroup ->
                println("教科名: $subjectName, 選択されたグループ: $selectedGroup")
            }
            SelectGroupScreen(
                rowIndex = rowIndex,
                columnIndex = columnIndex,
                user = user,
                groups = groups,
                popBackStack = popBackStack,
                navToHomeScreen = navToHomeScreen,
                onRegisterClick = onRegisterClick
            )
        }
    }
}
