package com.example.taskmate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

data class Class(
    val day: String,
    val classList: List<String>,
)

@Composable
fun HomeScreen(navToSettingScreen: () -> Unit) {
    val dayClassList = listOf("1限", "2限", "3限", "4限")

    val mockClassList =
        listOf(
            Class(day = "月", classList = listOf("プロマネ", "シス工", "実験", "実験")),
            Class(day = "火", classList = listOf("英語", "電磁気", "情報理論", "")),
            Class(day = "水", classList = listOf("制御演習", "数学", "卒研", "")),
            Class(day = "木", classList = listOf("ネト応", "人文", "卒研", "卒研")),
            Class(day = "金", classList = listOf("信号処理", "他コース", "制御理論", "")),
        )

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "グループ名", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
            TimeSchedule(dayClassList, mockClassList) // 　時間割
        }
    }
}

@Composable
fun TimeSchedule(
    dayClassList: List<String>,
    mockClassList: List<Class>,
) {
    DayOfWeekList(mockClassList.map { it.day }) // 月火水木金
    DayClassList(dayClassList, mockClassList)
}


@Composable
fun DayOfWeekList(dayOfWeekList: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        dayOfWeekList.forEach { day ->
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                Modifier
                    .width(50.dp)
                    .height(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(size = 5.dp),
                    ),
            ) {
                Text(text = day, color = MaterialTheme.colorScheme.background)
            }
        }
    }
}


@Composable
fun DayClassList(
    dayClassList: List<String>,
    mockClassList: List<Class>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            dayClassList.forEach { day ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                    Modifier
                        .width(50.dp)
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(size = 5.dp),
                        ),
                ) {
                    Text(text = day, color = MaterialTheme.colorScheme.background)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            mockClassList.forEachIndexed { index, classData ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    classData.classList.forEachIndexed { classIndex, className ->
                        val boxColor =
                            if (index % 2 == 0 && classIndex % 2 == 0 || (index % 2 == 1 && classIndex % 2 == 1)) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier =
                            Modifier
                                .width(50.dp)
                                .height(60.dp)
                                .background(
                                    color = boxColor,
                                    shape = RoundedCornerShape(size = 5.dp),
                                ),
                        ) {
                            Text(text = className)
                        }
                    }
                }
            }
        }
    }
}
