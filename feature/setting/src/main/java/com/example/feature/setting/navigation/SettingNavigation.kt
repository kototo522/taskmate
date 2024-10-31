package com.example.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.SettingNavigation
import com.example.core.model.navigation.SettingNavigation.Companion.CREATE_GROUP_ROUTE
import com.example.core.model.navigation.SettingNavigation.Companion.JOIN_GROUP_ROUTE
import com.example.core.model.navigation.SettingNavigation.Companion.LOGOUT_ROUTE
import com.example.core.model.navigation.SettingNavigation.Companion.SETTING_GRAPH_ROUTE
import com.example.core.model.navigation.SettingNavigation.Companion.SETTING_ROUTE
import com.example.feature.setting.SettingScreen
import com.example.feature.setting.settingItemScreen.createGroup.CreateGroup
import com.example.feature.setting.settingItemScreen.joinGroup.JoinGroup
import com.example.feature.setting.settingItemScreen.logout.LogoutScreen

fun NavGraphBuilder.settingNavGraph(
    settingController: SettingNavigation,
    user: TaskMateUser?,
) {
    val popBackStack: () -> Unit = { settingController.popBackStack() }

    navigation(
        startDestination = SETTING_ROUTE,
        route = SETTING_GRAPH_ROUTE,
    ) {
        composable(route = SETTING_ROUTE) {
            SettingScreen(settingController, popBackStack)
        }
        composable(route = LOGOUT_ROUTE) {
            LogoutScreen(popBackStack)
        }
        composable(route = CREATE_GROUP_ROUTE) {
            if (user != null) {
                CreateGroup(popBackStack = popBackStack, createUserId = user.userId)
            }
        }
        composable(route = JOIN_GROUP_ROUTE) {
            if (user != null) {
                JoinGroup(popBackStack = popBackStack, joinUserId = user.userId)
            }
        }
    }
}
