package com.example.taskmate.ui.appBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskmate.R

@Composable
fun MainTaskMateAppBar(navToSettingScreen: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    TaskMateAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = context.getString(R.string.app_name_jp),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
            )
        },
        onNavigationClick = navToSettingScreen,
        navigation = {},
        popBackClick = {},
        modifier = modifier,
    )
}

@Preview
@Composable
fun PreviewMainTaskMateAppBar() {
    MainTaskMateAppBar(
        navToSettingScreen = {},
        modifier = Modifier,
    )
}
