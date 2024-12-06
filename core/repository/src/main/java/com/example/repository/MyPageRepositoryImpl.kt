package com.example.repository

import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor() : MyPageRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun fetchUserData(userId: String): TaskMateUser? {
        return try {
            val document = firestore.collection("users")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .documents
                .firstOrNull()
            document?.toObject(TaskMateUser::class.java)
        } catch (exception: Exception) {
            null
        }
    }

    override suspend fun fetchAllGroups(): List<TaskMateGroup> {
        return try {
            val groupDocuments = firestore.collection("groups")
                .get()
                .await()
                .documents
            groupDocuments.mapNotNull { it.toObject(TaskMateGroup::class.java) }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun fetchSubjects(): List<TaskMateSubject> {
        return try {
            val subjectDocuments = firestore.collection("subjects")
                .get()
                .await()
                .documents
            subjectDocuments.mapNotNull { it.toObject(TaskMateSubject::class.java) }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun updateUserGroups(userId: String, groupIds: List<String>): Boolean {
        return try {
            firestore.collection("users").document(userId)
                .update("groupId", groupIds)
                .await()
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun deleteGroupFromUser(userId: String, groupId: String): Boolean {
        return try {
            firestore.collection("users").document(userId)
                .update("groupId", FieldValue.arrayRemove(groupId))
                .await()
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun changeUserIcon(userId: String, imageUrl: String): Boolean {
        return try {
            firestore.collection("users").document(userId)
                .update("iconUrl", imageUrl)
                .await()
            true
        } catch (exception: Exception) {
            false
        }
    }
}
