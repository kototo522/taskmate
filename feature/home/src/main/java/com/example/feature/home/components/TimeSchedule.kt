package com.example.feature.home.components

import androidx.compose.runtime.Composable
import com.example.core.model.Class

@Composable
fun TimeSchedule(
    dayClassList: List<String>,
    classList: List<Class>,
    navToSubjectListScreen: (String, Int, Int?) -> Unit,
) {
    WeekList(classList.map { it.day }) // 月火水木金
    ClassList(dayClassList, classList) { clickedClass, rowIndex, columnIndex ->
        val daysOfWeek = listOf("月曜日", "火曜日", "水曜日", "木曜日", "金曜日")

        val dayName = if (rowIndex in daysOfWeek.indices) daysOfWeek[rowIndex] else "不明な曜日"
        val period = columnIndex?.plus(1) ?: "N/A"
        println("Clicked mock class on $dayName, ${period}限目, class: $clickedClass")
        navToSubjectListScreen(clickedClass, rowIndex, columnIndex)
    }
}
