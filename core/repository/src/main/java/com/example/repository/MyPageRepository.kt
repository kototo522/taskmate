package com.example.repository

import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser

interface MyPageRepository {
    suspend fun fetchUserData(userId: String): TaskMateUser?
    suspend fun fetchAllGroups(): List<TaskMateGroup>
    suspend fun fetchSubjects(): List<TaskMateSubject>
    suspend fun updateUserGroups(userId: String, groupIds: List<String>): Boolean
    suspend fun deleteGroupFromUser(userId: String, groupId: String): Boolean
    suspend fun changeUserIcon(userId: String, imageUrl: String): Boolean
}
