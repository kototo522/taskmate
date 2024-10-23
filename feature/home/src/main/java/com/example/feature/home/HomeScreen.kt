package com.example.taskmate.ui.home

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
import com.example.taskmate.ui.home.components.TimeSchedule

@Composable
fun HomeScreen(navToSettingScreen: () -> Unit) {
    val dayClassList = listOf("1限", "2限", "3限", "4限")

    val mockClassList =
        listOf(
            Class(
                day = "月",
                classList = listOf("プロマネ", "シス工", "実験", "実験"),
            ),
            Class(
                day = "火",
                classList = listOf("英語", "電磁気", "情報理論", ""),
            ),
            Class(
                day = "水",
                classList = listOf("制御演習", "数学", "卒研", ""),
            ),
            Class(
                day = "木",
                classList = listOf("ネト応", "人文", "卒研", "卒研"),
            ),
            Class(day = "金", classList = listOf("信号処理", "他コース", "制御理論", "")),
        )

    Scaffold(
        topBar = {
//            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "グループ名", fontSize = 18.sp, fontWeight = FontWeight(600), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(20.dp))
            TimeSchedule(dayClassList, mockClassList) // 　時間割
        }
    }
}
