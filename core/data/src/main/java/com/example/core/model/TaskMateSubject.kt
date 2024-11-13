package com.example.core.model

import java.util.Date

data class TaskMateSubject(
    val subjectId: String = "",
    val name: String = "",
    val groupId: String = "",
    val columnIndex: List<Int> = listOf(0),
    val rowIndex: List<Int> = listOf(0),
    val createdAt: Date = Date(),
) {
    constructor() : this("", "", "", listOf(0), listOf(0), Date())
}
