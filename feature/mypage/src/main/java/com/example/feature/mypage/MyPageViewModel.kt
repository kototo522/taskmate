package com.example.feature.mypage

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyPageViewModel : ViewModel() {
    private var errorMessage: String = ""
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var users by mutableStateOf<List<TaskMateUser>>(emptyList())
    private var user by mutableStateOf<TaskMateUser?>(null)

    fun fetchUserData() {
        firestore.collection("users")
            .whereEqualTo("userId", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                users = documents.map { it.toObject(TaskMateUser::class.java) }
                user = users.find { it.userId == auth.currentUser?.uid }
                if (user == null) {
                    Log.e("HomeViewModel", "User not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("HomeViewModel", "Error fetching user data", exception)
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

    fun userGroupUpdate(
        userId: String,
        groups: List<TaskMateGroup>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) { // ここにスペースを追加
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

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.JAPAN)
        return formatter.format(date)
    }
}
