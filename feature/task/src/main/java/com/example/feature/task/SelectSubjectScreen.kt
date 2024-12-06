package com.example.feature.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.model.TaskMateSubject
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.feature.task.components.SubjectCard

@Composable
fun SelectSubjectScreen(
    navToAddTaskScreen: (TaskMateSubject) -> Unit,
    popBackStack: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val user by remember { viewModel.userState }
    val groups by remember { viewModel.groupsState }
    val subjects by remember { viewModel.subjectsState }
    val userSubjects by remember { viewModel.userSubjects }

    LaunchedEffect(user) {
        if (user == null) {
            viewModel.fetchAllData()
        }
    }

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(context.getString(TaskMateStrings.AddTask), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
        containerColor = MaterialTheme.colorScheme.background, // 背景色をテーマに合わせる
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                items(userSubjects.size) { index ->
                    val subject = userSubjects[index]
                    val group = groups.firstOrNull { it.groupId == subject.groupId }
                    SubjectCard(
                        subject = subject,
                        group = group,
                        onClick = { subject -> navToAddTaskScreen(subject) },
                    )
                }
            }
        }
    }
}
