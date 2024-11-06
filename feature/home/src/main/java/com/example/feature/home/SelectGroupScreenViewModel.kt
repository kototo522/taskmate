package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Date

class SelectGroupScreenViewModel : ViewModel()  {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createSubject(
        subjectName: String,
        groupId: String,
        rowIndex: Int,
        columnIndex: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val subjectData = mapOf(
                    "name" to subjectName,
                    "groupId" to groupId,
                    "rowIndex" to rowIndex,
                    "columnIndex" to columnIndex,
                    "createAt" to Date(),
                )

                firestore.collection("subjects")
                    .add(subjectData)
                    .addOnSuccessListener { documentReference ->
                        val subjectId = documentReference.id
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
