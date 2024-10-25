package com.example.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.navigation.AuthNavigation
import com.example.feature.auth.FirstAuthScreen
import com.example.feature.auth.LoginScreen
import com.example.feature.auth.SignUpScreen

const val AUTH_GRAPH_ROUTE = "AuthGraph"
internal const val FIRST_AUTH_ROUTE = "FirstAuthScreen"
internal const val LOGIN_ROUTE = "LoginScreen"
internal const val SIGNUP_ROUTE = "SignUpScreen"
fun NavGraphBuilder.authNavGraph(
    authController: AuthNavigation
) {
    val navToHomeScreen: () -> Unit = { authController.navToHomeScreen }
    val navToLoginScreen: () -> Unit = authController.navToLoginScreen
    val navToSignUpScreen: () -> Unit = authController.navToSignUpScreen
    val popBackStack: () -> Unit = { authController.popBackStack() }

    navigation(
        startDestination = FIRST_AUTH_ROUTE,
        route = AUTH_GRAPH_ROUTE
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
