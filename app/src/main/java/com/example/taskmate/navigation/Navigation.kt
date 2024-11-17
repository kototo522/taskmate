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
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.AuthNavigation.Companion.AUTH_GRAPH_ROUTE
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.model.navigation.HomeNavigation.Companion.HOME_GRAPH_ROUTE
import com.example.core.model.navigation.SettingNavigation.Companion.SETTING_GRAPH_ROUTE
import com.example.core.ui.taskmateComponents.BottomNavBar
import com.example.feature.auth.navigation.authNavGraph
import com.example.feature.home.navigation.homeNavGraph
import com.example.feature.mypage.MyPageScreen
import com.example.feature.setting.navigation.settingNavGraph
import com.example.feature.task.navigation.taskNavGraph
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(
    modifier: Modifier,
    users: List<TaskMateUser>,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    subjects: List<TaskMateSubject>,
) {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    var isUserAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    // ユーザ情報があるか確認
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
    val homeNavigation = navController.home()
    val taskNavigation = navController.task()
    val settingNavigation = navController.setting()
    val authNavigation = navController.auth()

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navStackBackEntry?.destination?.route
    val navToSettingScreen = { navController.navigate(SETTING_GRAPH_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }

    Scaffold(
        bottomBar = {
            if (isUserAuthenticated) {
                BottomNavBar(screenItems = screenItems, currentRoute = currentRoute, navController = navController)
            }
        },
    ) {
        Box(modifier = modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = if (isUserAuthenticated) HOME_GRAPH_ROUTE else AUTH_GRAPH_ROUTE,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                authNavGraph(authNavigation)

                if (isUserAuthenticated) {
                    settingNavGraph(settingNavigation, user)
                    homeNavGraph(homeNavigation, user, groups, subjects)
                    taskNavGraph(taskNavigation, users, user, groups, subjects)
                }
                composable(route = BottomNavBarItems.MyPage.route) {
                    if (isUserAuthenticated) {
                        MyPageScreen(navToSettingScreen, user, groups)
                    }
                }
            }
        }
    }
}
