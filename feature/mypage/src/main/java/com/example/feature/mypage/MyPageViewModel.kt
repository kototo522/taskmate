package com.example.feature.mypage

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    ) { // ここにスペースを追加
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

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.JAPAN)
        return formatter.format(date)
    }
}
