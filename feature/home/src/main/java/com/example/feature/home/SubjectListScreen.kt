package com.example.feature.home

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
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.feature.home.components.SubjectListCard

@Composable
fun SubjectListScreen(
    clickedClass: String?,
    rowIndex: Int,
    columnIndex: Int,
    navToSelectGroupScreen: (Int, Int?) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current
    val daysOfWeek = listOf("月曜日", "火曜日", "水曜日", "木曜日", "金曜日")
    val dayName = daysOfWeek.getOrElse(rowIndex) { "不明な曜日" }
    val period = (columnIndex + 1).toString()
    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = {
                    Text(text = "$dayName ${period}限目", color = MaterialTheme.colorScheme.secondary)
                },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                FloatingActionButton(
                    onClick = { navToSelectGroupScreen(rowIndex, columnIndex) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                ) {
                    Text(text = context.getString(TaskMateStrings.AddIcon), fontSize = 24.sp)
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier.align(Alignment.End)) {
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(3) { index ->
                    SubjectListCard()
                }
            }
        }
    }
}
