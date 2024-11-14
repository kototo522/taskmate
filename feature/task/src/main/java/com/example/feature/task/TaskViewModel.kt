package com.example.feature.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
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
        remindTime: String,
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
                    "remindTime" to remindTime,
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
        }
    }
}
