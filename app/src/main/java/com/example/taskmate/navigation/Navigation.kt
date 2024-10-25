package com.example.taskmate.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.model.TaskMateSubject
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.ui.taskmateComponents.BottomNavBar
import com.example.feature.auth.navigation.AUTH_GRAPH_ROUTE
import com.example.feature.auth.navigation.authNavGraph
import com.example.feature.home.HomeScreen
import com.example.feature.mypage.MyPageScreen
import com.example.feature.setting.SettingScreen
import com.example.feature.task.AddTaskScreen
import com.example.feature.task.SelectSubjectScreen
import com.example.feature.task.TaskScreen
import com.example.taskmate.ui.setting.settingItemScreen.CreateGroup
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    var isUserAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    DisposableEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            isUserAuthenticated = auth.currentUser != null
        }
        auth.addAuthStateListener(authStateListener)

        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    val screenItems = listOf(
        BottomNavBarItems.Home,
        BottomNavBarItems.Task,
        BottomNavBarItems.MyPage,
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route
    val navToSettingScreen = { navController.navigate("SettingScreen") }
    val navToSelectSubjectScreen = { navController.navigate("SelectSubjectScreen") }
    val navToAddTaskScreen: (TaskMateSubject) -> Unit = { navController.navigate("AddTaskScreen") }
    val popBackStack: () -> Unit = { navController.popBackStack() }
    val authNavigation = navController.auth()

    Scaffold(
        bottomBar = {
            if (isUserAuthenticated) {
                BottomNavBar(screenItems = screenItems, currentRoute = currentRoute, navController = navController)
            }
        }
    ) {
        Box(modifier = modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = if (isUserAuthenticated) BottomNavBarItems.Home.route else AUTH_GRAPH_ROUTE,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                authNavGraph(authNavigation)

                composable(route = BottomNavBarItems.Home.route) {
                    if (isUserAuthenticated) {
                        HomeScreen(navToSettingScreen)
                    }
                }
                composable(route = BottomNavBarItems.Task.route) {
                    if (isUserAuthenticated) {
                        TaskScreen(navToSettingScreen, navToSelectSubjectScreen)
                    }
                }
                composable(route = BottomNavBarItems.MyPage.route) {
                    if (isUserAuthenticated) {
                        MyPageScreen(navToSettingScreen)
                    }
                }
                composable(route = "SettingScreen") {
                    if (isUserAuthenticated) {
                        SettingScreen(popBackStack)
                    }
                }
                composable(route = "SelectSubjectScreen") {
                    if (isUserAuthenticated) {
                        SelectSubjectScreen(navToAddTaskScreen, popBackStack)
                    }
                }
                composable(route = "AddTaskScreen") {
                    if (isUserAuthenticated) {
                        AddTaskScreen(popBackStack = popBackStack)
                    }
                }
                composable(route = "CreateGroup") {
                    if (isUserAuthenticated) {
                        CreateGroup(popBackStack = popBackStack)
                    }
                }
            }
        }
    }
}
