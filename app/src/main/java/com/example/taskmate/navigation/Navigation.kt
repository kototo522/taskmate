package com.example.taskmate.navigation
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
import com.example.taskmate.ui.HomeScreen
import com.example.taskmate.ui.MyPageScreen
import com.example.taskmate.ui.SettingScreen
import com.example.taskmate.ui.TaskScreen

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
    val popBackStack: () -> Unit = { navController.popBackStack() }

    Scaffold(
        bottomBar = {
            BottomNavBar(screenItems = screenItems, currentRoute = currentRoute, navController = navController)
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavBarItems.Home.route,
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
                    TaskScreen(navToSettingScreen)
                }
                composable(route = BottomNavBarItems.MyPage.route) {
                    MyPageScreen(navToSettingScreen)
                }
                composable(route = "SettingScreen") {
                    SettingScreen(popBackStack)
                }
            }
        }
    }
}
