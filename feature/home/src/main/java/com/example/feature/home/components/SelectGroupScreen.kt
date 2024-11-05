package com.example.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar

@Composable
fun SelectGroupScreen(
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = {
                    Text(text = "教科の登録", color = MaterialTheme.colorScheme.secondary)
                },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        }
    }
}
