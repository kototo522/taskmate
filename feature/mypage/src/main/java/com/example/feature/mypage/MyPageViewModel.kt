package com.example.feature.mypage

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel(
    private val repository: MyPageRepository,
) : ViewModel() {

    private val _userState = mutableStateOf<TaskMateUser?>(null)
    val userState: State<TaskMateUser?> get() = _userState

    private val _groups = mutableStateOf<List<TaskMateGroup>>(emptyList())
    val groupsState: State<List<TaskMateGroup>> get() = _groups

    private val _subjects = mutableStateOf<List<TaskMateSubject>>(emptyList())
    val subjectsState: State<List<TaskMateSubject>> get() = _subjects

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            try {
                _userState.value = repository.fetchUserData(userId)
            } catch (exception: Exception) {
                _errorMessage.value = "Error fetching user data: ${exception.message}"
            }
        }
    }

    fun fetchAllGroups() {
        viewModelScope.launch {
            try {
                _groups.value = repository.fetchAllGroups()
            } catch (exception: Exception) {
                _errorMessage.value = "Error fetching groups: ${exception.message}"
            }
        }
    }

    fun fetchSubjects() {
        viewModelScope.launch {
            try {
                _subjects.value = repository.fetchSubjects()
            } catch (exception: Exception) {
                _errorMessage.value = "Error fetching subjects: ${exception.message}"
            }
        }
    }

    fun updateUserGroups(userId: String, groups: List<TaskMateGroup>) {
        viewModelScope.launch {
            try {
                if (repository.updateUserGroups(userId, groups.map { it.groupId })) {
                    Log.d("MyPageViewModel", "Groups updated successfully")
                } else {
                    _errorMessage.value = "Failed to update user groups"
                }
            } catch (exception: Exception) {
                _errorMessage.value = "Error updating groups: ${exception.message}"
            }
        }
    }

    fun deleteGroup(userId: String, groupId: String) {
        viewModelScope.launch {
            try {
                if (repository.deleteGroupFromUser(userId, groupId)) {
                    Log.d("MyPageViewModel", "Group deleted successfully")
                } else {
                    _errorMessage.value = "Failed to delete group"
                }
            } catch (exception: Exception) {
                _errorMessage.value = "Error deleting group: ${exception.message}"
            }
        }
    }

    fun changeIcon(userId: String, imageUrl: Uri) {
        viewModelScope.launch {
            try {
                if (repository.changeUserIcon(userId, imageUrl.toString())) {
                    Log.d("MyPageViewModel", "Icon changed successfully")
                } else {
                    _errorMessage.value = "Failed to change icon"
                }
            } catch (exception: Exception) {
                _errorMessage.value = "Error changing icon: ${exception.message}"
            }
        }
    }
}
