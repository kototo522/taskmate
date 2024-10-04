package com.example.taskmate.ui.appBar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.taskmate.R

@Composable
fun MainTaskMateAppBar(navToSettingScreen: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    TaskMateAppBar(
        title = { Text(context.getString(R.string.app_name_jp), color = MaterialTheme.colorScheme.secondary) },
        actions = { Icon(painter = painterResource(id = R.drawable.setting), contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
        onNavigationClick = navToSettingScreen,
        navigation = {},
        popBackClick = {},
        modifier = modifier,
    )
}