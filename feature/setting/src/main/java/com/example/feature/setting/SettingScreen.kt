package com.example.feature.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.model.SettingItem
import com.example.core.model.navigation.SettingNavigation
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons
import com.example.feature.setting.componets.SettingCard

@Composable
fun SettingScreen(
    settingNavigation: SettingNavigation,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current

    val settingsItems = listOf(
        SettingItem(title = "ログアウト", icon = TaskMateIcons.Setting, onClick = settingNavigation.navToLogoutScreen),
        SettingItem(title = "Notifications", subTitle = "Notification history, conversations", icon = TaskMateIcons.Setting),
        SettingItem(title = "Battery", subTitle = "100%", icon = TaskMateIcons.Setting),
    )

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(text = context.getString(TaskMateStrings.Setting), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                items(settingsItems.size) { item ->
                    SettingCard(settingsItems[item])
                }
            }
        }
    }
}
