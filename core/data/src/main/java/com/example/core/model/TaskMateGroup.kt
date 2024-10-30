package com.example.core.model

import java.util.Date

data class TaskMateGroup(
    val groupId: String,
    val groupName: String,
    val password: String,
    val createUserId: String,
    val createdAt: Date,
    val lastUpdatedAt: Date,
) {
    constructor() : this("", "", "", "", Date(), Date())
}
