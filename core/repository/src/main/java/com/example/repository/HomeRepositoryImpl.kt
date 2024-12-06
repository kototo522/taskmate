package com.example.repository

import android.util.Log
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : HomeRepository {
    override suspend fun fetchUserData(userId: String): TaskMateUser? {
        return try {
            val document = db.collection("users")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .documents
                .firstOrNull()

            document?.toObject(TaskMateUser::class.java)
        } catch (exception: Exception) {
            Log.e("RepositoryImpl", "Error fetching user data", exception)
            null
        }
    }

    override suspend fun fetchAllGroups(): List<TaskMateGroup> {
        return try {
            val groupDocuments = db.collection("groups")
                .get()
                .await()

            groupDocuments.map { it.toObject(TaskMateGroup::class.java) }
        } catch (exception: Exception) {
            Log.e("RepositoryImpl", "Error fetching groups", exception)
            emptyList()
        }
    }

    override suspend fun fetchSubjects(): List<TaskMateSubject> {
        return try {
            val subjectDocuments = db.collection("subjects")
                .get()
                .await()

            subjectDocuments.map { it.toObject(TaskMateSubject::class.java) }
        } catch (exception: Exception) {
            Log.e("RepositoryImpl", "Error fetching subjects", exception)
            emptyList()
        }
    }
}
