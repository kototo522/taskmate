package com.example.feature.setting.settingItemScreen.joinGroup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class JoinGroupViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var errorMessage: String = ""
    var groupName by mutableStateOf("")
    var password by mutableStateOf("")

    fun joinGroup(
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        firestore.collection("groups")
            .whereEqualTo("groupName", groupName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val groupId = querySnapshot.documents[0].id
                    firestore.collection("users").document(userId)
                        .update(
                            "groupId",
                            FieldValue.arrayUnion(groupId),
                            "pastGroupId",
                            FieldValue.arrayUnion(groupId),
                        )
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { exception ->
                            errorMessage = exception.message ?: "ユーザー情報の更新に失敗しました。"
                            onFailure(errorMessage)
                        }
                } else {
                    onFailure("指定されたグループは存在しません。")
                }
            }
            .addOnFailureListener { exception ->
                errorMessage = exception.message ?: "グループの検索に失敗しました。"
                onFailure(errorMessage)
            }
    }
}
