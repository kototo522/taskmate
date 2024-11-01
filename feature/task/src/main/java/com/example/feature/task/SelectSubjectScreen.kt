package com.example.feature.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.model.TaskMateSubject
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.feature.task.components.SubjectCard

@Composable
fun SelectSubjectScreen(
    navToAddTaskScreen: (TaskMateSubject) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current
    val subjects = listOf(
        TaskMateSubject("数学", Color(0xFF42A5F5)), // 青
        TaskMateSubject("英語", Color(0xFF66BB6A)), // 緑
        TaskMateSubject("歴史", Color(0xFFFFC107)), // 黄
    )

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(context.getString(TaskMateStrings.AddIcon), color = MaterialTheme.colorScheme.secondary) },
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
                items(subjects.size) { index ->
                    SubjectCard(subject = subjects[index], onClick = { navToAddTaskScreen(subjects[index]) })
                }
            }
        }
    }
}
