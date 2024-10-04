package com.example.taskmate.ui.addTask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.PopBackTaskMateAppBar

data class Subject(
    val name: String,
    val color: Color
)

@Composable
fun SelectSubjectScreen(popBackStack: () -> Unit) {
    val context = LocalContext.current
    val subjects = listOf(
        Subject("数学", Color(0xFF42A5F5)),
        Subject("英語", Color(0xFF66BB6A)),
        Subject("歴史", Color(0xFFFFC107)),
    )

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(context.getString(R.string.add_task), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn {
                items(subjects) { subject ->
                    SubjectCard(subject = subject, onClick = {})
                }
            }
        }
    }
}


