package com.example.taskmate.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.model.TaskMateSubject
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.ui.taskmateComponents.BottomNavBar
import com.example.feature.auth.FirstAuthScreen
import com.example.feature.auth.LoginScreen
import com.example.feature.auth.SignUpScreen
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
    val screenItems = listOf(
        BottomNavBarItems.Home,
        BottomNavBarItems.Task,
        BottomNavBarItems.MyPage,
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route
    val navToHomeScreen = { navController.navigate(BottomNavBarItems.Home.route) }
    val navToSettingScreen = { navController.navigate("SettingScreen") }
    val navToSelectSubjectScreen = { navController.navigate("SelectSubjectScreen") }
    val navToAddTaskScreen: (TaskMateSubject) -> Unit = { navController.navigate("AddTaskScreen") }
    val navToLoginScreen: () -> Unit = { navController.navigate("LoginScreen") }
    val navToSignUpScreen: () -> Unit = { navController.navigate("SignUpScreen") }
    val popBackStack: () -> Unit = { navController.popBackStack() }

    val auth = FirebaseAuth.getInstance()
    val isUserAuthenticated = auth.currentUser != null

    LaunchedEffect(isUserAuthenticated) {
        if (!isUserAuthenticated && currentRoute != "LoginScreen" && currentRoute != "SignUpScreen") {
            navController.navigate("FirstAuthScreen") {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (isUserAuthenticated) {
                BottomNavBar(screenItems = screenItems, currentRoute = currentRoute, navController = navController)
            }
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = if (isUserAuthenticated) BottomNavBarItems.Home.route else "FirstAuthScreen",
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
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
                composable(route = "FirstAuthScreen") {
                    FirstAuthScreen(navToLoginScreen, navToSignUpScreen)
                }
                composable(route = "SignUpScreen") {
                    SignUpScreen(navToHomeScreen = navToHomeScreen, popBackStack = popBackStack)
                }
                composable(route = "LoginScreen") {
                    LoginScreen(navToHomeScreen = navToHomeScreen, popBackStack = popBackStack)
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
