package com.example.taskmate.ui.task

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
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.MainTaskMateAppBar

data class Task(
    val title: String,
//    val destination: String,
//    val deadlineDate: Date,
//    val deadlineTime: Time,
//    val remind: Int,
)

@Composable
fun TaskScreen(
    navToSettingScreen: () -> Unit,
    navToSelectSubjectScreen: () -> Unit,
) {
    val context = LocalContext.current
    val tasks = listOf(
        Task("タスク1"),
        Task("タスク2"),
        Task("タスク3"),
        Task("タスク4"),
        Task("タスク5"),
        Task("タスク6"),
        Task("タスク7"),
        Task("タスク8"),
        Task("タスク9"),
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
                    Text(text = context.getString(R.string.add_icon), fontSize = 24.sp)
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
            Column() {
                Box(modifier = Modifier.align(Alignment.End)) {
                }
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
}
