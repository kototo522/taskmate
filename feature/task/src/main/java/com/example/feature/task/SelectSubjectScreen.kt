package com.example.feature.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.feature.task.components.SubjectCard
import java.util.Date

@Composable
fun SelectSubjectScreen(
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    subjects: List<TaskMateSubject>,
    navToAddTaskScreen: (TaskMateSubject) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current

    val userGroupIds = remember { mutableStateOf(user?.groupId.orEmpty()) }
    val userSubjects = subjects.filter { subject ->
        userGroupIds.value.contains(subject.groupId)
    }

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
                items(userSubjects.size) { index ->
                    val subject = userSubjects[index]
                    val groupName = groups.firstOrNull { it.groupId == subject.groupId }?.groupName ?: "Unknown Group"
                    SubjectCard(
                        subject = subject,
                        groupName = groupName,
                        onClick = { navToAddTaskScreen(subject) }
                    )
                }
            }
        }
    }
}
