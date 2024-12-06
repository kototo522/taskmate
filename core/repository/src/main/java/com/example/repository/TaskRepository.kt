package com.example.repository

import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateTask
import com.example.core.model.TaskMateUser

interface TaskRepository {
    suspend fun fetchUserData(userId: String): TaskMateUser?
    suspend fun fetchAllGroups(): List<TaskMateGroup>
    suspend fun fetchSubjects(): List<TaskMateSubject>
    suspend fun createTask(taskData: Map<String, Any?>, subjectId: String): Result<Unit>
    suspend fun fetchTasks(): Result<List<TaskMateTask>>
    suspend fun deleteTask(taskId: String): Result<Unit>
    suspend fun updateTask(taskId: String, updatedData: Map<String, Any>): Result<Unit>
}
