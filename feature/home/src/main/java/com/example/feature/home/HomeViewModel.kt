package com.example.feature.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var users by mutableStateOf<List<TaskMateUser>>(emptyList())
    private var user by mutableStateOf<TaskMateUser?>(null)
    private var groups by mutableStateOf<List<TaskMateGroup>>(emptyList())
    private var subjects by mutableStateOf<List<TaskMateSubject>>(emptyList())

    fun fetchAllData() {
        fetchUserData { success ->
            if (success) {
                fetchAllGroups()
                fetchSubjects()
            }
        }
    }

    private fun fetchUserData(callback: (Boolean) -> Unit) {
        db.collection("users")
            .whereEqualTo("userId", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                users = documents.map { it.toObject(TaskMateUser::class.java) }
                user = users.find { it.userId == auth.currentUser?.uid }
                if (user == null) {
                    Log.e("HomeViewModel", "User not found")
                    callback(false)
                } else {
                    callback(true)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("HomeViewModel", "Error fetching user data", exception)
                callback(false)
            }
    }

    private fun fetchAllGroups() {
        db.collection("groups")
            .get()
            .addOnSuccessListener { groupDocuments ->
                groups = groupDocuments.map { groupDocument ->
                    val group = groupDocument.toObject(TaskMateGroup::class.java)
                    group
                }
            }
            .addOnFailureListener { exception ->
                exception.localizedMessage?.let { Log.e("groupsDBError", it) }
            }
    }

    private fun fetchSubjects() {
        db.collection("subjects")
            .get()
            .addOnSuccessListener { subjectDocuments ->
                subjects = subjectDocuments.map { subjectDocument ->
                    subjectDocument.toObject(TaskMateSubject::class.java)
                }
            }
            .addOnFailureListener { exception ->
                exception.localizedMessage?.let { Log.e("subjectsDBError", it) }
            }
    }
}
