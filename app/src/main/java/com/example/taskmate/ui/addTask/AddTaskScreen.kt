package com.example.taskmate.ui.addTask
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmate.ui.appBar.PopBackTaskMateAppBar

@Composable
fun AddTaskScreen(popBackStack: () -> Unit) {
    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(text = "教科がありません", color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        )
    }
}