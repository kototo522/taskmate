package com.example.taskmate.navigation
import SelectSubjectScreen
import Subject
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmate.ui.addTask.AddTaskScreen
import com.example.taskmate.ui.auth.FirstAuthScreen
import com.example.taskmate.ui.auth.LoginScreen
import com.example.taskmate.ui.auth.SignUpScreen
import com.example.taskmate.ui.home.HomeScreen
import com.example.taskmate.ui.mypage.MyPageScreen
import com.example.taskmate.ui.setting.SettingScreen
import com.example.taskmate.ui.setting.settingItemScreen.CreateGroup
import com.example.taskmate.ui.task.TaskScreen

@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()
    val screenItems =
        listOf(
            BottomNavBarItems.Home,
            BottomNavBarItems.Task,
            BottomNavBarItems.MyPage,
        )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route
    val navToSettingScreen = { navController.navigate("SettingScreen") }
    val navToSelectSubjectScreen = { navController.navigate("SelectSubjectScreen") }
    val navToAddTaskScreen: (Subject) -> Unit = { navController.navigate("AddTaskScreen") }
    val navToLoginScreen: () -> Unit = { navController.navigate("LoginScreen") }
    val navToSignUpScreen: () -> Unit = { navController.navigate("SignUpScreen") }
    val popBackStack: () -> Unit = { navController.popBackStack() }

    Scaffold(
        bottomBar = {
            BottomNavBar(screenItems = screenItems, currentRoute = currentRoute, navController = navController)
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = "CreateGroup",
                enterTransition = {
                    EnterTransition.None // 画面遷移時のアニメーション無効化
                },
                exitTransition = {
                    ExitTransition.None // 画面遷移時のアニメーション無効化
                },
            ) {
                composable(route = BottomNavBarItems.Home.route) {
                    HomeScreen(navToSettingScreen)
                }
                composable(route = BottomNavBarItems.Task.route) {
                    TaskScreen(navToSettingScreen, navToSelectSubjectScreen)
                }
                composable(route = BottomNavBarItems.MyPage.route) {
                    MyPageScreen(navToSettingScreen)
                }
                composable(route = "SettingScreen") {
                    SettingScreen(popBackStack)
                }
                composable(route = "FirstAuthScreen") {
                    FirstAuthScreen(navToLoginScreen, navToSignUpScreen)
                }
                composable(route = "SignUpScreen") {
                    SignUpScreen(popBackStack = popBackStack)
                }
                composable(route = "LoginScreen") {
                    LoginScreen(popBackStack = popBackStack)
                }
                composable(route = "SelectSubjectScreen") {
                    SelectSubjectScreen(navToAddTaskScreen, popBackStack)
                }
                composable(route = "AddTaskScreen") {
                    AddTaskScreen(popBackStack = popBackStack)
                }
                composable(route = "CreateGroup") {
                    CreateGroup(popBackStack = popBackStack)
                }
            }
        }
    }
}
