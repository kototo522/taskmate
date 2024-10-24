package com.example.core.ui.taskmateComponents

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.core.model.navigation.BottomNavBarItems

@Composable
fun BottomNavBar(screenItems: List<BottomNavBarItems>, currentRoute: String?, navController: NavHostController) {
    NavigationBar(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.background),
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        screenItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = screen.icon), contentDescription = null) },
                label = { Text(text = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
