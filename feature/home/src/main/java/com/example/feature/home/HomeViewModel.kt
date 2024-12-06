package com.example.feature.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {
    private val _userState = mutableStateOf<TaskMateUser?>(null)
    val userState: State<TaskMateUser?> get() = _userState

    private val _groups = mutableStateOf<List<TaskMateGroup>>(emptyList())
    val groupsState: State<List<TaskMateGroup>> get() = _groups

    private val _subjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val subjectsState: State<List<TaskMateSubject>> get() = _subjects

    fun fetchAllData() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
            val user = repository.fetchUserData(userId)
            _userState.value = user

            if (user != null) {
                _groups.value = repository.fetchAllGroups()
                _subjects.value = repository.fetchSubjects()
            }
        }
    }
}
