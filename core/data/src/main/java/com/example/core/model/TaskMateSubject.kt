package com.example.core.model

import java.util.Date

data class TaskMateSubject(
    val id: String = "",
    val name: String = "",
    val groupId: String = "",
    val columnIndex: Int = 0,
    val rowIndex: Int = 0,
    val createdAt: Date = Date()
) {
    constructor() : this("", "", "", 0, 0, Date())
}
