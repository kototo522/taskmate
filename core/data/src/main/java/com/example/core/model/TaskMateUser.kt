package com.example.core.model

data class TaskMateUser(
    val userId: String = "",
    val userName: String = "ユーザーネーム",
    val email: String = "sample@gmail.com",
    val iconUrl: String? = null,
    val groupId: List<String?> = emptyList(),
    val pastGroupId: List<String?> = emptyList(),
) {
    constructor() : this("", "ユーザーネーム", "sample@gmail.com", null, emptyList(), emptyList())
}
