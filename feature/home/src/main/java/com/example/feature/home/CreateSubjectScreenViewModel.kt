package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Date

class CreateSubjectScreenViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createSubject(
        subjectName: String,
        groupId: String,
        rowIndex: Int,
        columnIndex: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val newSubjectRef = firestore.collection("subjects").document() // ここで新しいドキュメント参照を作成
        val subjectId = newSubjectRef.id

        viewModelScope.launch {
            try {
                val subjectData = mapOf(
                    "subjectId" to subjectId,
                    "name" to subjectName,
                    "groupId" to groupId,
                    "rowIndex" to listOf(rowIndex),
                    "columnIndex" to listOf(columnIndex),
                    "createAt" to Date(),
                )

                newSubjectRef.set(subjectData)
                    .addOnSuccessListener {
                        // 教科作成後、groupドキュメントを更新
                        firestore.collection("groups").document(groupId)
                            .update("subjectIds", FieldValue.arrayUnion(subjectId))
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.message ?: "グループの更新に失敗しました。")
                            }
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "教科の作成に失敗しました。")
                    }
            } catch (e: Exception) {
                onFailure(e.message ?: "不明なエラーが発生しました。")
            }
        }
    }
}
