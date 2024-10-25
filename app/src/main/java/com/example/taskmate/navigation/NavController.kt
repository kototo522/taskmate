package com.example.taskmate.navigation

import androidx.navigation.NavController
import com.example.core.model.navigation.AuthNavigation

fun NavController.auth(): AuthNavigation {
    return AuthNavigation(this)
}
