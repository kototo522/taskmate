package com.example.feature.home.components

import androidx.compose.runtime.Composable
import com.example.core.model.Class

@Composable
fun TimeSchedule(
    dayClassList: List<String>,
    mockClassList: List<Class>,
) {
    WeekList(mockClassList.map { it.day }) // 月火水木金
    ClassList(dayClassList, mockClassList)
}
