package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
    navToSubjectListScreen: (String, Int, Int?) -> Unit,
) {
    val dayClassList = listOf("1限", "2限", "3限", "4限")
    val userGroupIds = remember { mutableStateOf(user?.groupId.orEmpty()) }
    val userGroups by remember(userGroupIds.value) {
        derivedStateOf {
            groups.filter { group ->
                userGroupIds.value.contains(group.groupId)
            }
        }
    }
    val classList = remember(subjects) {
        val days = listOf("月", "火", "水", "木", "金")
        days.map { day ->
            Class(
                day = day,
                classList = MutableList(4) { "" }
            )
        }.toMutableList()
    }

    subjects.forEach { subject ->
        val dayIndex = subject.rowIndex // 0: 月, 1: 火, ...
        val periodIndex = subject.columnIndex // 0: 1限, 1: 2限, ...
        classList[dayIndex].classList[periodIndex] = subject.name
    }

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "グループ名", fontSize = 18.sp, fontWeight = FontWeight(600), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(20.dp))
            TimeSchedule(dayClassList, classList, navToSubjectListScreen) // Pass updated classList
        }
    }
}
