package com.example.feature.setting.settingItemScreen.createGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CreateGroupViewModel : ViewModel() {
    var groupName by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val firestore = FirebaseFirestore.getInstance()

    fun createGroup(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                // TODO: 保存するデータの型と数を後々決める
//                // Firestoreに保存するデータ
//                val groupData = hashMapOf(
//                    "groupName" to groupName,
//                    "password" to password
//                )
//
//                // コレクション名 "groups" にドキュメントを追加
//                firestore.collection("groups")
//                    .add(groupData)
//                    .await()
//
//                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
