package com.example.core.model.navigation

import androidx.navigation.NavController

class HomeNavigation(private val navController: NavController)

class TaskNavigation(private val navController: NavController)

class MyPageNavigation(private val navController: NavController)

class SettingNavigation(private val navController: NavController)

class AuthNavigation(private val navController: NavController) {
    val navToHomeScreen: () -> Unit = { navController.navigate(BottomNavBarItems.Home.route) }
    val navToLoginScreen: () -> Unit = { navController.navigate("LoginScreen") }
    val navToSignUpScreen: () -> Unit = { navController.navigate("SignUpScreen") }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}
