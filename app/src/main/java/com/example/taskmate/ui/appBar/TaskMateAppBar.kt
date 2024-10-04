package com.example.taskmate.ui.appBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskMateAppBar(
    title: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    onNavigationClick: () -> Unit,
    navigation: @Composable () -> Unit,
    popBackClick: () -> Unit,
    modifier: Modifier
) {
    Column {
        TopAppBar(
            title = title,
            actions = {
                IconButton(onClick = onNavigationClick) {
                    actions()
                }
            },
            navigationIcon = {
                IconButton(onClick = popBackClick) {
                    navigation()
                }
            },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = modifier.fillMaxWidth().height(2.dp).background(MaterialTheme.colorScheme.onSecondary).alpha(1f))
    }
}

@Preview
@Composable
fun PreviewTaskMateAppBar(){
    val context = LocalContext.current
    TaskMateAppBar(
        title = {Text(text = context.getString(R.string.app_name_jp), color = MaterialTheme.colorScheme.secondary)},
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        },
        navigation = {},
        popBackClick = {},
        onNavigationClick = {},
        modifier = Modifier
    )
}