package com.example.repository

import com.example.core.model.TaskMateTask
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun createTask(taskData: Map<String, Any?>, subjectId: String): Result<Unit> {
        return try {
            val taskId = firestore.collection("tasks").document().id
            val newTaskData = taskData.toMutableMap().apply { put("taskId", taskId) }
            firestore.collection("tasks").document(taskId).set(newTaskData).await()
            firestore.collection("subjects").document(subjectId)
                .update("taskIds", FieldValue.arrayUnion(taskId)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchTasks(): Result<List<TaskMateTask>> {
        return try {
            val tasks = firestore.collection("tasks").get().await()
                .toObjects(TaskMateTask::class.java)
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            firestore.collection("tasks").document(taskId).delete().await()
            val query = firestore.collection("subjects").whereArrayContains("taskIds", taskId).get().await()
            for (doc in query.documents) {
                doc.reference.update("taskIds", FieldValue.arrayRemove(taskId)).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(taskId: String, updatedData: Map<String, Any>): Result<Unit> {
        return try {
            firestore.collection("tasks").document(taskId).update(updatedData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
