package com.example.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var errorMessage: String = ""
        private set
    fun signUp(email: String, password: String, username: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        errorMessage = task.exception?.message ?: "サインアップに失敗しました。"
                        onFailure(errorMessage)
                    }
                }
        }
    }
}
