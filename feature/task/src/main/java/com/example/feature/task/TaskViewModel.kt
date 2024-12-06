package com.example.feature.task

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateTask
import com.example.core.model.TaskMateUser
import com.example.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {
    private val _userState = mutableStateOf<TaskMateUser?>(null)
    val userState: State<TaskMateUser?> get() = _userState

    private val _groups = mutableStateOf<List<TaskMateGroup>>(emptyList())
    val groupsState: State<List<TaskMateGroup>> get() = _groups

    private val _subjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val subjectsState: State<List<TaskMateSubject>> get() = _subjects

    private val _tasks = mutableStateOf<List<TaskMateTask>>(emptyList())
    val tasksState: State<List<TaskMateTask>> get() = _tasks

    private val _userSubjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val userSubjects: State<List<TaskMateSubject>> get() = _userSubjects

    private val _sortedTasks = mutableStateOf<List<TaskMateTask>>(emptyList())
    val sortedTasks: State<List<TaskMateTask>> get() = _sortedTasks

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun fetchAllData() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
            val user = repository.fetchUserData(userId)
            _userState.value = user

            if (user != null) {
                _groups.value = repository.fetchAllGroups()
                _subjects.value = repository.fetchSubjects()

                val tasksResult = repository.fetchTasks()
                tasksResult.onSuccess { tasks ->
                    _tasks.value = tasks
                    calculateUserSubjects(user.groupId)
                    updateSortedTasks(user.groupId, tasks)
                }.onFailure {
                    Log.e("TaskViewModel", "Error fetching tasks: ${it.message}")
                }
            }
        }
    }

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

    private fun calculateUserSubjects(userGroupIds: List<String?>) {
        _userSubjects.value = _subjects.value.filter { it.groupId in userGroupIds }
    }

    private fun updateSortedTasks(userGroupIds: List<String?>, tasks: List<TaskMateTask>) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/M/d")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        _sortedTasks.value = tasks
            .filter { it.groupId in userGroupIds }
            .sortedBy { task ->
                val deadlineDate = LocalDate.parse(task.deadlineDate, dateFormatter)
                val deadlineTime = LocalTime.parse(task.deadlineTime, timeFormatter)
                deadlineDate.atTime(deadlineTime)
            }
    }
}
