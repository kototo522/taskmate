package com.example.taskmate.ui.appBar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskmate.R

@Composable
fun PopBackTaskMateAppBar(
    title: @Composable () -> Unit,
    popBackScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TaskMateAppBar(
        title = title,
        actions = {},
        onNavigationClick = {},
        navigation = { Icon(painter = painterResource(id = R.drawable.back_arrow), contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
        popBackClick = popBackScreen,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PreviewPopBackTaskMateAppBar() {
    PopBackTaskMateAppBar(
        title = { Text("設定", color = MaterialTheme.colorScheme.secondary) },
        popBackScreen = {},
        modifier = Modifier,
    )
}
