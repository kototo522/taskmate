package com.example.taskmate.navigation

import androidx.navigation.NavController
import com.example.core.model.navigation.AuthNavigation
import com.example.core.model.navigation.HomeNavigation
import com.example.core.model.navigation.SettingNavigation
import com.example.core.model.navigation.TaskNavigation

fun NavController.home(): HomeNavigation {
    return HomeNavigation(this)
}

fun NavController.task(): TaskNavigation {
    return TaskNavigation(this)
}

fun NavController.setting(): SettingNavigation {
    return SettingNavigation(this)
}

fun NavController.auth(): AuthNavigation {
    return AuthNavigation(this)
}
