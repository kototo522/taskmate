package com.example.feature.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userState = mutableStateOf<TaskMateUser?>(null)
    val userState: State<TaskMateUser?> get() = _userState

    private val _groups = mutableStateOf<List<TaskMateGroup>>(emptyList())
    val groupsState: State<List<TaskMateGroup>> get() = _groups

    private val _subjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val subjectsState: State<List<TaskMateSubject>> get() = _subjects

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
                val users = documents.map { it.toObject(TaskMateUser::class.java) }
                val user = users.find { it.userId == auth.currentUser?.uid }
                _userState.value = user
                callback(user != null)
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
                _groups.value = groupDocuments.map { it.toObject(TaskMateGroup::class.java) }
            }
            .addOnFailureListener { exception ->
                Log.e("groupsDBError", exception.localizedMessage.orEmpty())
            }
    }

    private fun fetchSubjects() {
        db.collection("subjects")
            .get()
            .addOnSuccessListener { subjectDocuments ->
                _subjects.value = subjectDocuments.map { it.toObject(TaskMateSubject::class.java) }
            }
            .addOnFailureListener { exception ->
                Log.e("subjectsDBError", exception.localizedMessage.orEmpty())
            }
    }
}
