package com.example.taskmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.taskmate.navigation.Navigation
import com.example.taskmate.ui.theme.TaskmateTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var users by mutableStateOf<List<TaskMateUser>>(emptyList())
    private var user by mutableStateOf<TaskMateUser?>(null)
    private var groups by mutableStateOf<List<TaskMateGroup>>(emptyList())
    private var subjects by mutableStateOf<List<TaskMateSubject>>(emptyList())
    private var isUserAuthenticated by mutableStateOf(auth.currentUser != null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskmateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding), users = users, user = user, groups = groups, subjects = subjects)
                }
            }
            // Firestoreからユーザデータを取得
            LaunchedEffect(isUserAuthenticated, users, groups, db) {
                db.collection("users")
                    .whereEqualTo("userId", auth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        users = documents.map { document ->
                            document.toObject(TaskMateUser::class.java)
                        }
                        // 現在のユーザーを取得
                        user = users.find { it.userId == auth.currentUser?.uid }
                        if (user == null) {
                            Log.e("user", "User not found")
                        } else {
                            fetchAllGroups()
                            fetchSubjects()
                        }
                    }
                    .addOnFailureListener { exception ->
                        exception.localizedMessage?.let { Log.e("UsersDBError", it) }
                    }
            }
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
