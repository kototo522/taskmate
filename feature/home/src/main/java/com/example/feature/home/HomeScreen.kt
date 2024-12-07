package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.model.Class
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.home.components.TimeSchedule

@Composable
fun HomeScreen(
    navToSettingScreen: () -> Unit,
    navToSubjectListScreen: (Int, Int?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val fetchedUser by remember { viewModel.userState }
    val fetchedSubject by remember { viewModel.subjectsState }
    val dayClassList = listOf("1限", "2限", "3限", "4限")
    val userGroupIds = remember { mutableStateOf(fetchedUser?.groupId.orEmpty()) }
    val filteredSubjects = remember(userGroupIds.value, fetchedSubject) {
        fetchedSubject.filter { subject ->
            userGroupIds.value.contains(subject.groupId)
        }
    }
    val classList = remember(fetchedSubject) {
        val days = listOf("月", "火", "水", "木", "金")
        days.map { day ->
            Class(
                day = day,
                classList = MutableList(4) { "" },
            )
        }.toMutableList()
    }

    LaunchedEffect(fetchedUser) {
        if (fetchedUser == null) {
            viewModel.fetchAllData()
        } else {
            userGroupIds.value = fetchedUser!!.groupId
        }
    }

    filteredSubjects.forEach { subject ->
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
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(vertical = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TimeSchedule(dayClassList, classList, navToSubjectListScreen)
        }
    }
}
