package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Class
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.home.components.TimeSchedule

@Composable
fun HomeScreen(
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    subjects: List<TaskMateSubject>,
    navToSettingScreen: () -> Unit,
    navToSubjectListScreen: (Int, Int?) -> Unit,
) {
    val dayClassList = listOf("1限", "2限", "3限", "4限")
    val userGroupIds = remember { mutableStateOf(user?.groupId.orEmpty()) }
    val classList = remember(subjects) {
        val days = listOf("月", "火", "水", "木", "金")
        days.map { day ->
            Class(
                day = day,
                classList = MutableList(4) { "" },
            )
        }.toMutableList()
    }

    subjects.forEach { subject ->
        subject.rowIndex.forEach { dayIndex ->
            subject.columnIndex.forEach { periodIndex ->
                if (dayIndex in 0..4 && periodIndex in 0..3) {
                    classList[dayIndex].classList[periodIndex] = subject.name
                }
            }
        }
    }

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "グループ名", fontSize = 18.sp, fontWeight = FontWeight(600), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(20.dp))
            TimeSchedule(dayClassList, classList, navToSubjectListScreen)
        }
    }
}
