package com.example.feature.task

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateTask
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateOf<List<TaskMateTask>>(emptyList())
    val tasks: List<TaskMateTask> get() = _tasks.value
    private var errorMessage: String = ""
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
            try {
                val newTaskRef = firestore.collection("tasks").document()
                val taskId = newTaskRef.id

                val taskData = mapOf(
                    "userId" to userId,
                    "taskId" to taskId,
                    "groupId" to groupId,
                    "subjectId" to subjectId,
                    "title" to title,
                    "destination" to destination,
                    "deadlineDate" to deadlineDate,
                    "deadlineTime" to deadlineTime,
                    "visibility" to visibility,
                    "remindTime" to false,
                )

                firestore.collection("tasks").document(taskId)
                    .set(taskData)
                    .addOnSuccessListener {
                        firestore.collection("subjects").document(subjectId)
                            .update("taskIds", FieldValue.arrayUnion(taskId))
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { exception ->
                                errorMessage = exception.message ?: "タスクの作成に失敗しました。"
                                onFailure(errorMessage)
                            }
                    }
                    .addOnFailureListener { exception ->
                        errorMessage = exception.message ?: "タスクの保存に失敗しました。"
                        onFailure(errorMessage)
                    }
            } catch (e: Exception) {
                onFailure(e.message ?: "不明なエラーが発生しました。")
            }
            fetchTask()
        }
    }

    fun fetchTask() {
        viewModelScope.launch {
            try {
                val taskList = mutableListOf<TaskMateTask>()
                firestore.collection("tasks")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val task = document.toObject(TaskMateTask::class.java)
                            taskList.add(task)
                        }
                        _tasks.value = taskList // Update the tasks list
                    }
                    .addOnFailureListener { exception ->
                        // Handle error
                        Log.e("TaskViewModel", "Error getting tasks: ${exception.message}")
                    }
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error fetching tasks: ${e.message}")
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            try {
                firestore.collection("tasks").document(taskId)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("TaskViewModel", "Task deleted successfully: $taskId")

                        firestore.collection("subjects")
                            .whereArrayContains("taskIds", taskId)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                for (document in querySnapshot) {
                                    document.reference.update("taskIds", FieldValue.arrayRemove(taskId))
                                        .addOnSuccessListener {
                                            Log.d("TaskViewModel", "Task ID removed from subject.")
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.e("TaskViewModel", "Error removing task ID from subject: ${exception.message}")
                                        }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.e("TaskViewModel", "Error finding related subjects: ${exception.message}")
                            }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TaskViewModel", "Error deleting task: ${exception.message}")
                    }
                fetchTask()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Unexpected error during task deletion: ${e.message}")
            }
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

        firestore.collection("tasks").document(taskId)
            .update(updatedData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "更新に失敗しました。")
            }
    }
}
