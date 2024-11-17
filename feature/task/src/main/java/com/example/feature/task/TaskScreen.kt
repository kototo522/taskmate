package com.example.feature.task

import androidx.compose.foundation.layout.Box
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
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.task.components.TaskCard

@Composable
fun TaskScreen(
    users: List<TaskMateUser>,
    group: List<TaskMateGroup>,
    subjects: List<TaskMateSubject>,
    navToSettingScreen: () -> Unit,
    navToSelectSubjectScreen: () -> Unit,
    viewModel: TaskViewModel = viewModel(),
) {
    viewModel.fetchTask()
    val context = LocalContext.current
    val tasks = viewModel.tasks

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
                    val groupName = group.firstOrNull { it.groupId == tasks[task].groupId }?.groupName ?: "Unknown Group"
                    val subjectsName = subjects.firstOrNull { it.subjectId == tasks[task].subjectId }?.name ?: "Unknown Subject"
                    TaskCard(groupName, subjectsName, tasks[task], Modifier.padding(16.dp))
                }
            }
        }
    }
}
