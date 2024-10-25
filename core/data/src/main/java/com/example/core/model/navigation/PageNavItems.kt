package com.example.core.model.navigation

import androidx.navigation.NavController
import com.example.core.model.navigation.AuthNavigation.Companion.LOGIN_ROUTE

class HomeNavigation(private val navController: NavController)

class TaskNavigation(private val navController: NavController)

class MyPageNavigation(private val navController: NavController)

class SettingNavigation(private val navController: NavController) {
    companion object {
        const val SETTING_GRAPH_ROUTE = "SettingGraph"
        const val SETTING_ROUTE = "SettingScreen"
        const val CREATE_GROUP_ROUTE = "CreateGroup"
        const val LOGOUT_ROUTE = "LogOut"
    }
    val navToLogoutScreen: () -> Unit = { navController.navigate(LOGOUT_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}

class AuthNavigation(private val navController: NavController) {
    companion object {
        const val AUTH_GRAPH_ROUTE = "AuthGraph"
        const val FIRST_AUTH_ROUTE = "FirstAuthScreen"
        const val LOGIN_ROUTE = "LoginScreen"
        const val SIGNUP_ROUTE = "SignUpScreen"
    }
    val navToHomeScreen: () -> Unit = { navController.navigate(BottomNavBarItems.Home.route) }
    val navToLoginScreen: () -> Unit = { navController.navigate(LOGIN_ROUTE) }
    val navToSignUpScreen: () -> Unit = { navController.navigate(SIGNUP_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}
