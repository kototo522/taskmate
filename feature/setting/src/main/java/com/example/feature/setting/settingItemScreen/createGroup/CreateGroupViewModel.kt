package com.example.feature.setting.settingItemScreen.createGroup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Date

class CreateGroupViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var errorMessage: String = ""
    var groupName by mutableStateOf("")
    var password by mutableStateOf("")

    fun createGroup(
        createUserId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val groupId = firestore.collection("groups").document().id // グループIDを生成
                val groupData = mapOf(
                    "groupID" to groupId,
                    "groupName" to groupName,
                    "password" to password,
                    "createUserID" to createUserId,
                    "createdAt" to Date(),
                    "lastUpdatedAt" to Date(),
                )

                firestore.collection("groups").document(groupId)
                    .set(groupData)
                    .addOnSuccessListener {
                        updateUserGroup(createUserId, groupId, onSuccess, onFailure)
                        onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        errorMessage = exception.message ?: "グループ作成に失敗しました。"
                        onFailure(errorMessage)
                    }
            } catch (e: Exception) {
                onFailure(e.message ?: "不明なエラーが発生しました。")
            }
        }
    }

    private fun updateUserGroup(
        userId: String,
        groupId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        firestore.collection("users").document(userId)
            .update(
                "groupID", FieldValue.arrayUnion(groupId), // groupsIDリストに追加
                "pastGroupID", FieldValue.arrayUnion(groupId) // pastGroupIDリストにも追加
            )
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                errorMessage = exception.message ?: "ユーザー情報の更新に失敗しました。"
                onFailure(errorMessage)
            }
    }
}
