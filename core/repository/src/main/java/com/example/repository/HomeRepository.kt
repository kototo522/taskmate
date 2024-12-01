package com.example.repository

import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser

interface HomeRepository {
    suspend fun fetchUserData(userId: String): TaskMateUser?
    suspend fun fetchAllGroups(): List<TaskMateGroup>
    suspend fun fetchSubjects(): List<TaskMateSubject>
}
