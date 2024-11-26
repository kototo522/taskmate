package com.example.feature.mypage

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var errorMessage: String = ""

    private val _userState = mutableStateOf<TaskMateUser?>(null)
    val userState: State<TaskMateUser?> get() = _userState

    private val _groups = mutableStateOf<List<TaskMateGroup>>(emptyList())
    val groupsState: State<List<TaskMateGroup>> get() = _groups

    private val _subjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val subjectsState: State<List<TaskMateSubject>> get() = _subjects

    fun fetchUserData() {
        firestore.collection("users")
            .whereEqualTo("userId", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                val user = documents.map { it.toObject(TaskMateUser::class.java) }
                    .find { it.userId == auth.currentUser?.uid }
                _userState.value = user
                if (user == null) {
                    Log.e("MyPageViewModel", "User not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MyPageViewModel", "Error fetching user data", exception)
            }
    }

    fun fetchAllGroups() {
        firestore.collection("groups")
            .get()
            .addOnSuccessListener { groupDocuments ->
                _groups.value = groupDocuments.map { it.toObject(TaskMateGroup::class.java) }
            }
            .addOnFailureListener { exception ->
                Log.e("groupsDBError", exception.localizedMessage.orEmpty())
            }
    }

    fun fetchSubjects() {
        firestore.collection("subjects")
            .get()
            .addOnSuccessListener { subjectDocuments ->
                _subjects.value = subjectDocuments.map { it.toObject(TaskMateSubject::class.java) }
            }
            .addOnFailureListener { exception ->
                Log.e("subjectsDBError", exception.localizedMessage.orEmpty())
            }
    }

    fun userGroupUpdate(
        userId: String,
        groups: List<TaskMateGroup>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val groupIds = groups.map { it.groupId }
                firestore.collection("users").document(userId)
                    .update("groupId", groupIds)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { exception ->
                        errorMessage = exception.message ?: "ユーザー情報の更新に失敗しました。"
                        onFailure(errorMessage)
                    }
            } catch (e: Exception) {
                onFailure(e.message ?: "不明なエラーが発生しました。")
            }
        }
    }

    fun deleteGroup(
        userId: String,
        groupId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                firestore.collection("users").document(userId)
                    .update("groupId", FieldValue.arrayRemove(groupId))
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        errorMessage = exception.message ?: "グループの削除に失敗しました。"
                        Log.e("MyPageViewModel", errorMessage)
                        onFailure(errorMessage)
                    }
            } catch (e: Exception) {
                val error = e.message ?: "不明なエラーが発生しました。"
                Log.e("MyPageViewModel", error)
                onFailure(error)
            }
        }
    }

    fun changeIcon(
        userId: String,
        imageUrl: Uri,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                firestore.collection("users").document(userId)
                    .update("iconUrl", imageUrl.toString())
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { exception ->
                        errorMessage = exception.message ?: "ユーザー情報の更新に失敗しました。"
                        onFailure(errorMessage)
                    }
            } catch (e: Exception) {
                onFailure(e.message ?: "不明なエラーが発生しました。")
            }
        }
    }
}
