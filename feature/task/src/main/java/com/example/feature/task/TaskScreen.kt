package com.example.feature.task

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.model.TaskMateTask
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.task.components.TaskCard
import com.example.feature.task.components.TaskCardModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navToSettingScreen: () -> Unit,
    navToSelectSubjectScreen: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val user by remember { viewModel.userState }
    val group by remember { viewModel.groupsState }
    val subjects by remember { viewModel.subjectsState }
    val sortedTasks by remember { viewModel.sortedTasks }
    Log.e("tasks", sortedTasks.toString())
    LaunchedEffect(user) {
        if (user == null) {
            viewModel.fetchAllData()
        }
    }
    val context = LocalContext.current
    val selectedTask = remember { mutableStateOf<TaskMateTask?>(null) }
    val selectedGroup = remember { mutableStateOf<String?>(null) }
    val selectedSubject = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isCardClick = remember { mutableStateOf(false) }

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
