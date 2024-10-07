package com.example.taskmate.ui.home.components

import androidx.compose.runtime.Composable
import com.example.taskmate.ui.home.Class

@Composable
fun TimeSchedule(
    dayClassList: List<String>,
    mockClassList: List<Class>,
) {
    WeekList(mockClassList.map { it.day }) // 月火水木金
    ClassList(dayClassList, mockClassList)
}
