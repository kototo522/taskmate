package com.example.core.model

data class TaskMateTask(
    val taskId: String,
    val userId: String,
    val groupId: String,
    val subjectId: String,
    val title: String,
    val destination: String,
    val deadlineDate: String,
    val deadlineTime: String,
    val visibility: String,
    val remindTime: String,
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "")
}
