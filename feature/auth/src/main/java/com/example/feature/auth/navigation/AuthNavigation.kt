package com.example.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.navigation.AuthNavigation
import com.example.core.model.navigation.AuthNavigation.Companion.AUTH_GRAPH_ROUTE
import com.example.core.model.navigation.AuthNavigation.Companion.FIRST_AUTH_ROUTE
import com.example.core.model.navigation.AuthNavigation.Companion.LOGIN_ROUTE
import com.example.core.model.navigation.AuthNavigation.Companion.SIGNUP_ROUTE
import com.example.feature.auth.FirstAuthScreen
import com.example.feature.auth.LoginScreen
import com.example.feature.auth.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    authController: AuthNavigation,
) {
    val navToHomeScreen: () -> Unit = { authController.navToHomeScreen }
    val navToLoginScreen: () -> Unit = authController.navToLoginScreen
    val navToSignUpScreen: () -> Unit = authController.navToSignUpScreen
    val popBackStack: () -> Unit = { authController.popBackStack() }

    navigation(
        startDestination = FIRST_AUTH_ROUTE,
        route = AUTH_GRAPH_ROUTE,
    ) {
        composable(FIRST_AUTH_ROUTE) {
            FirstAuthScreen(navToLoginScreen, navToSignUpScreen)
        }
        composable(LOGIN_ROUTE) {
            LoginScreen(navToHomeScreen = navToHomeScreen, popBackStack = popBackStack)
        }
        composable(SIGNUP_ROUTE) {
            SignUpScreen(navToHomeScreen = navToHomeScreen, popBackStack = popBackStack)
        }
    }
}
