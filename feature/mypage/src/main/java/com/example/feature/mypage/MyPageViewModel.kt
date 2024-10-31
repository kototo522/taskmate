package com.example.feature.mypage

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private var errorMessage: String = ""
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
        groupIds: List<TaskMateGroup>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ){
        viewModelScope.launch {
            try {
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
}
