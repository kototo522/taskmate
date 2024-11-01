package com.example.feature.mypage

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateUser
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons
import com.example.feature.mypage.components.DetailGroupModal
import com.example.feature.mypage.components.EditTagCardModal
import com.example.feature.mypage.components.TagCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navToSettingScreen: () -> Unit,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    viewModel: MyPageViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isEditGroupSheetOpen = remember { mutableStateOf(false) }
    val isDetailSheetOpen = remember { mutableStateOf(false) }

    val userGroupIds = remember { mutableStateOf(user?.groupId.orEmpty()) }
    val userPastGroupIds = remember { mutableStateOf(user?.pastGroupId.orEmpty()) }
    val selectedGroup = remember { mutableStateOf<TaskMateGroup?>(null) }

    val userGroups = remember(userGroupIds.value) {
        derivedStateOf {
            userGroupIds.value.let { ids ->
                groups.filter { group ->
                    ids.contains(group.groupId)
                }.map { group ->
                    group
                }
            }
        }
    }

    val userPastGroups = remember(userPastGroupIds.value) {
        derivedStateOf {
            userPastGroupIds.value.let { ids ->
                groups.filter { group ->
                    ids.contains(group.groupId)
                }.map { group ->
                    group
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null && user != null) {
                try {
                    // URI パーミッションの永続化
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION,
                    )
                } catch (e: SecurityException) {
                    Log.e("MyPage", "URI パーミッションの永続化に失敗しました: ${e.message}")
                }

                viewModel.changeIcon(
                    userId = user.userId,
                    imageUrl = uri,
                    onSuccess = {
                        Log.d("MyPage", "アイコンが正常に更新されました。")
                    },
                    onFailure = { error ->
                        Log.e("MyPage", "エラー: $error")
                    },
                )
            }
        },
    )

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 20.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .size(140.dp),
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))

                            user?.iconUrl?.let { Log.e("uri", it) }
                            AsyncImage(
                                model = user?.iconUrl?.let { Uri.parse(it) } ?: TaskMateIcons.Account,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape), // 丸くする
                                contentScale = ContentScale.Crop,
                            )
                        }
                        Text(
                            text = context.getString(TaskMateStrings.AddIcon),
                            fontSize = 38.sp,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable(
                                    indication = rememberRipple(
                                        radius = 20.dp,
                                        bounded = false,
                                    ),
                                    interactionSource = remember { MutableInteractionSource() },
                                ) {
                                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "ユーザ名",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    Text(
                        text = user?.userName ?: "ユーザーネーム",
                        fontSize = 28.sp,
                        fontWeight = FontWeight(700),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp),
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "所属グループ",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(5f),
                        )
                        // ボタンのリップルがズレるためBoxで囲む
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                        ) {
                            Text(
                                text = context.getString(TaskMateStrings.AddIcon),
                                fontSize = 24.sp,
                                modifier = Modifier.clickable(
                                    indication = rememberRipple(
                                        radius = 20.dp,
                                        bounded = false,
                                    ),
                                    interactionSource = remember { MutableInteractionSource() },
                                ) {
                                    scope.launch {
                                        isEditGroupSheetOpen.value = true
                                        sheetState.show()
                                    }
                                },
                            )
                        }
                    }

                    LazyRow(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        items(userGroups.value.size) { index ->
                            TagCard(userGroups.value[index].groupName) {
                                isDetailSheetOpen.value = true
                                selectedGroup.value = userGroups.value[index]
                            }
                        }
                    }
                }
            }
        }

        if (isEditGroupSheetOpen.value) {
            EditTagCardModal(
                group = userGroups.value,
                pastGroup = userPastGroups.value,
                scope = scope,
                sheetState = sheetState,
                isSheetOpen = isEditGroupSheetOpen,
                onSave = { selectedGroupIds ->
                    viewModel.userGroupUpdate(
                        userId = user?.userId ?: "",
                        groupIds = selectedGroupIds,
                        onSuccess = { Log.d("MyPage", "グループ情報が正常に更新されました。") },
                        onFailure = { error -> Log.e("MyPage", "エラー: $error") },
                    )
                },
            )
        }
        if (isDetailSheetOpen.value) {
            DetailGroupModal(
                group = selectedGroup,
                scope = scope,
                sheetState = sheetState,
                isSheetOpen = isDetailSheetOpen,
            )
        }
    }
}
