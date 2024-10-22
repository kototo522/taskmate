package com.example.feature.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.model.SettingItem
import com.example.feature.R
import com.example.feature.setting.componets.SettingCard

@Composable
fun SettingScreen(popBackStack: () -> Unit) {
    val context = LocalContext.current

    val settingsItems = listOf(
        SettingItem(title = "Apps", subTitle = "Assistant, recent apps, default apps", icon = R.drawable.setting),
        SettingItem(title = "Notifications", subTitle = "Notification history, conversations", icon = R.drawable.setting),
        SettingItem(title = "Battery", subTitle = "100%", icon = R.drawable.setting),
    )

    Scaffold(
        topBar = {
//            PopBackTaskMateAppBar(
//                title = { Text(context.getString(R.string.setting), color = MaterialTheme.colorScheme.secondary) },
//                popBackScreen = popBackStack,
//                modifier = Modifier,
//            )
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
