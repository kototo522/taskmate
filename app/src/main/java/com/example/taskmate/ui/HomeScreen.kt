package com.example.taskmate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmate.ui.appBar.MainTaskMateAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun HomeScreen(navToSettingScreen: () -> Unit) {
    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Timetable()
        }
    }
}

@Composable
fun Timetable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

        }
        // ヘッダー行（曜日）
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f, fill = false),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeTableHeader(text = "Time")
            TimeTableHeader(text = "Mon")
            TimeTableHeader(text = "Tue")
            TimeTableHeader(text = "Wed")
            TimeTableHeader(text = "Thu")
            TimeTableHeader(text = "Fri")
        }
        TimeTableRow(time = "1限目", subjects = listOf("数学", "物理", "英語", "体育", "美術"))
        TimeTableRow(time = "2限目", subjects = listOf("化学", "生物", "歴史", "音楽", "数学"))
        TimeTableRow(time = "3限目", subjects = listOf("化学", "生物", "歴史", "音楽", "数学"))
        TimeTableRow(time = "4限目", subjects = listOf("化学", "生物", "歴史", "音楽", "数学"))
    }
}

@Composable
fun TimeTableHeader(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TimeTableRow(time: String, subjects: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeTableHeader(text = time)
        subjects.forEach { subject ->
            TimeTableCell(text = subject)
        }
    }
}

@Composable
fun TimeTableCell(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
