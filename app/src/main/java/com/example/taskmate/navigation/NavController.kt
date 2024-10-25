package com.example.taskmate.navigation

import androidx.navigation.NavController
import com.example.core.model.navigation.AuthNavigation
import com.example.core.model.navigation.SettingNavigation

fun NavController.setting(): SettingNavigation {
    return SettingNavigation(this)
}

fun NavController.auth(): AuthNavigation {
    return AuthNavigation(this)
}
