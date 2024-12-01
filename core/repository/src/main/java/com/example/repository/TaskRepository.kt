package com.example.repository

import com.example.core.model.TaskMateTask

interface TaskRepository {
    suspend fun createTask(taskData: Map<String, Any?>, subjectId: String): Result<Unit>
    suspend fun fetchTasks(): Result<List<TaskMateTask>>
    suspend fun deleteTask(taskId: String): Result<Unit>
    suspend fun updateTask(taskId: String, updatedData: Map<String, Any>): Result<Unit>
}
