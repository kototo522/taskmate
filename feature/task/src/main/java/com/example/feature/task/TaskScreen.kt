package com.example.feature.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.Task
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.task.components.TaskCard

@Composable
fun TaskScreen(
    navToSettingScreen: () -> Unit,
    navToSelectSubjectScreen: () -> Unit,
    viewModel: TaskViewModel = viewModel(),
) {
    val context = LocalContext.current
    val tasks = listOf(
        Task("タスク1"),
        Task("タスク2"),
        Task("タスク3"),
    )
    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                FloatingActionButton(
                    onClick = navToSelectSubjectScreen,
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                ) {
                    Text(text = context.getString(TaskMateStrings.AddIcon), fontSize = 24.sp)
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(tasks.size) { task ->
                    TaskCard(tasks[task], Modifier.padding(16.dp))
                }
            }
        }
    }
}
