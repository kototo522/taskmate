package com.example.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var errorMessage: String = ""

    fun signUp(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            saveUserDataToFirestore(
                                userId = user.uid,
                                userName = userName,
                                email = email,
                                onSuccess = onSuccess,
                                onFailure = onFailure
                            )
                        }
                    } else {
                        errorMessage = task.exception?.message ?: "サインアップに失敗しました。"
                        onFailure(errorMessage)
                    }
                }
        }
    }

    private fun saveUserDataToFirestore(
        userId: String,
        userName: String,
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        saveToFirestore(
            userId = userId,
            userName = userName,
            email = email,
            iconUrl = null,
            groupsID = null,
            pastGroupID = null,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    private fun saveToFirestore(
        userId: String,
        userName: String,
        email: String,
        iconUrl: String?,
        groupsID: String?,
        pastGroupID: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userData = mapOf(
            "userName" to userName,
            "email" to email,
            "createdAt" to Date(),
            "lastUpdatedAt" to Date(),
            "iconUrl" to iconUrl,
            "groupsID" to groupsID,
            "pastGroupID" to pastGroupID
        )

        firestore.collection("users").document(userId)
            .set(userData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception ->
                errorMessage = exception.message ?: "ユーザー情報の保存に失敗しました。"
                onFailure(errorMessage)
            }
    }
}
