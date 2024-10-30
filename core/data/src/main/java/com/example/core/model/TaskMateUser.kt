package com.example.core.model

data class TaskMateUser(
    val userId: String = "",
    val userName: String = "ユーザーネーム",
    val email: String = "sample@gmail.com",
    val iconUrl: String? = null,
    val groupsID: String? = null,
    val pastGroupID: String? = null,
) {
    constructor() : this("", "ユーザーネーム", "sample@gmail.com", null, null, null)
}
