package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class SelectListScreenViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun updateIndex(
        subjectId: String,
        columnIndex: Int,
        rowIndex: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            firestore.collection("subjects")
                .document(subjectId)
                .update(
                    "columnIndex",
                    FieldValue.arrayUnion(columnIndex),
                    "rowIndex",
                    FieldValue.arrayUnion(rowIndex),
                )
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onFailure(e.message ?: "グループの更新に失敗しました。")
                }
        }
    }
}
