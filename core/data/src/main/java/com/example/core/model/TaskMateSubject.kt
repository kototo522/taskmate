package com.example.core.model

import java.util.Date

data class TaskMateSubject(
    val subjectId: String = "",
    val name: String = "",
    val groupId: String = "",
    val columnIndex: List<Int> = emptyList(),
    val rowIndex: List<Int> = emptyList(),
    val createdAt: Date = Date(),
) {
    constructor() : this("", "", "", emptyList(), emptyList(), Date()) // Change this line as well
}
