package com.example.core.model.navigation

import com.example.data.R

sealed class BottomNavBarItems(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomNavBarItems(route = "home", title = "Home", icon = R.drawable.clock)
    object Task : BottomNavBarItems(route = "task", title = "Task", icon = R.drawable.task)
    object MyPage : BottomNavBarItems(route = "my_page", title = "MyPage", icon = R.drawable.account)
}
