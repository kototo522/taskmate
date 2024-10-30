package com.example.core.model

import java.util.Date

data class TaskMateGroup(
    val groupID: String,
    val groupName: String,
    val password: String,
    val createUserID: String,
    val createdAt: Date,
    val lastUpdatedAt: Date,
) {
    constructor() : this("", "", "", "", Date(), Date())
}
