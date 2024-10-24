package com.example.core.ui.taskmateComponents.appBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskMateAppBar(
    title: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    onNavigationClick: () -> Unit,
    navigation: @Composable () -> Unit,
    popBackClick: () -> Unit,
    modifier: Modifier,
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
                titleContentColor = MaterialTheme.colorScheme.secondary,
            ),
        )
        Spacer(modifier = modifier.fillMaxWidth().height(2.dp).background(MaterialTheme.colorScheme.onSecondary).alpha(1f))
    }
}
