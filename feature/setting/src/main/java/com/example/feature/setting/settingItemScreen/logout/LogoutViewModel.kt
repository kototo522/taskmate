package com.example.feature.setting.settingItemScreen.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun logout(onLogoutSuccess: () -> Unit, onLogoutFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                auth.signOut()
                _isLoading.value = false
                onLogoutSuccess()
            } catch (e: Exception) {
                _isLoading.value = false
                onLogoutFailure(e)
            }
        }
    }
}
