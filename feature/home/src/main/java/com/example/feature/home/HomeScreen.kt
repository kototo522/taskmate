package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Class
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.feature.home.components.TimeSchedule

@Composable
fun HomeScreen(
    navToSettingScreen: () -> Unit,
    navToSubjectListScreen: (String, String) -> Unit,
) {
    val dayClassList = listOf("1限", "2限", "3限", "4限")

    val classList =
        listOf(
            Class(
                day = "月",
                classList = listOf("", "", "", ""),
            ),
            Class(
                day = "火",
                classList = listOf("", "", "", ""),
            ),
            Class(
                day = "水",
                classList = listOf("", "", "", ""),
            ),
            Class(
                day = "木",
                classList = listOf("", "", "", ""),
            ),
            Class(
                day = "金",
                classList = listOf("", "", "", ""),
            ),
        )

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "グループ名", fontSize = 18.sp, fontWeight = FontWeight(600), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(20.dp))
            TimeSchedule(dayClassList, classList, navToSubjectListScreen) // 　時間割
        }
    }
}
