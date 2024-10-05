package com.example.taskmate.ui.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.taskmate.ui.appBar.MainTaskMateAppBar

@Composable
fun TaskScreen(navToSettingScreen: () -> Unit) {
    val context = LocalContext.current
    val tasks = listOf(
        "タスク1",
        "タスク2",
        "タスク3",
        "タスク4",
        "タスク5"
    )
    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column() {
                Box(modifier = Modifier.align(Alignment.End)) {

                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tasks.size) { task ->
                        TaskCard(tasks[task], Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
