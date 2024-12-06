package com.example.feature.task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateTask
import com.example.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {
    private val _tasks = mutableStateOf<List<TaskMateTask>>(emptyList())
    val tasks: List<TaskMateTask> get() = _tasks.value

    fun createTask(
        userId: String,
        groupId: String?,
        subjectId: String,
        title: String,
        destination: String,
        deadlineDate: String,
        deadlineTime: String,
        visibility: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val taskData: Map<String, Any?> = mapOf(
                "userId" to userId,
                "groupId" to groupId,
                "subjectId" to subjectId,
                "title" to title,
                "destination" to destination,
                "deadlineDate" to deadlineDate,
                "deadlineTime" to deadlineTime,
                "visibility" to visibility,
            )

            val result = repository.createTask(taskData, subjectId)
            result.onSuccess { onSuccess() }
                .onFailure { onFailure(it.message ?: "タスクの作成に失敗しました。") }
        }
    }

    fun fetchTask() {
        viewModelScope.launch {
            val result = repository.fetchTasks()
            result.onSuccess { _tasks.value = it }
                .onFailure { Log.e("TaskViewModel", "Error fetching tasks: ${it.message}") }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            val result = repository.deleteTask(taskId)
            result.onFailure { Log.e("TaskViewModel", "Error deleting task: ${it.message}") }
        }
    }

    fun updateTask(
        taskId: String,
        title: String,
        deadlineDate: String,
        deadlineTime: String,
        destination: String,
        visibility: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val updatedData = mapOf(
            "title" to title,
            "deadlineDate" to deadlineDate,
            "deadlineTime" to deadlineTime,
            "destination" to destination,
            "visibility" to visibility,
        )

        viewModelScope.launch {
            val result = repository.updateTask(taskId, updatedData)
            result.onSuccess { onSuccess() }
                .onFailure { onFailure(it.message ?: "更新に失敗しました。") }
        }
    }
}
