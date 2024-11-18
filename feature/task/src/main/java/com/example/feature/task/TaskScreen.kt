package com.example.feature.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateTask
import com.example.core.model.TaskMateUser
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.task.components.TaskCard
import com.example.feature.task.components.TaskCardModal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
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
    val selectedTask = remember { mutableStateOf<TaskMateTask?>(null) }
    val selectedGroup = remember { mutableStateOf<String?>(null) }
    val selectedSubject = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isCardClick = remember { mutableStateOf(false) }
    val sortedTasks = tasks.sortedBy { task ->
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val deadlineDate = LocalDate.parse(task.deadlineDate, dateFormatter)
        val deadlineTime = LocalTime.parse(task.deadlineTime, timeFormatter)
        val deadlineDateTime = deadlineDate.atTime(deadlineTime)
        deadlineDateTime
    }

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
                items(sortedTasks.size) { task ->
                    selectedGroup.value = group.firstOrNull { it.groupId == sortedTasks[task].groupId }?.groupName ?: "Unknown Group"
                    selectedSubject.value = subjects.firstOrNull { it.subjectId == sortedTasks[task].subjectId }?.name ?: "Unknown Subject"
                    val isChecked = remember { mutableStateOf(false) }
                    TaskCard(
                        selectedGroup.value!!,
                        selectedSubject.value!!,
                        sortedTasks[task],
                        isChecked,
                        onCardClick = {
                            selectedTask.value = sortedTasks[task]
                            isCardClick.value = true
                        },
                        Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    )
                }
            }
            val shouldShowModal = isCardClick.value &&
                selectedTask.value != null &&
                selectedGroup.value != null &&
                selectedSubject.value != null

            if (shouldShowModal) {
                TaskCardModal(
                    scope = scope,
                    sheetState = sheetState,
                    isSheetOpen = isCardClick,
                    task = selectedTask.value!!,
                    groupName = selectedGroup.value!!,
                    subjectName = selectedSubject.value!!,
                )
            }
        }
    }
}
