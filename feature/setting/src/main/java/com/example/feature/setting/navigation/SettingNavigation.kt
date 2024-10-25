package com.example.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.model.navigation.SettingNavigation
import com.example.feature.setting.SettingScreen
import com.example.feature.setting.settingItemScreen.CreateGroup

const val SETTING_GRAPH_ROUTE = "SettingGraph"
internal const val SETTING_ROUTE = "SettingScreen"
internal const val CREATE_GROUP__ROUTE = "CreateGroup"

fun NavGraphBuilder.settingNavGraph(
    settingController: SettingNavigation,
) {
    val popBackStack: () -> Unit = { settingController.popBackStack() }

    navigation(
        startDestination = SETTING_ROUTE,
        route = SETTING_GRAPH_ROUTE,
    ) {
        composable(route = SETTING_ROUTE) {
            SettingScreen(popBackStack)
        }
        composable(route = CREATE_GROUP__ROUTE) {
            CreateGroup(popBackStack = popBackStack)
        }
    }
}
