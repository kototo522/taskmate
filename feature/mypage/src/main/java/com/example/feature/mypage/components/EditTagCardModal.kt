package com.example.feature.mypage.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Tag
import com.example.core.model.TaskMateGroup
import com.example.core.ui.taskmateComponents.TaskMateAlertDialog
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTagCardModal(
    group: List<TaskMateGroup>,
    pastGroup: List<TaskMateGroup>,
    scope: CoroutineScope,
    sheetState: SheetState,
    isSheetOpen: MutableState<Boolean>,
    onSave: (List<TaskMateGroup>) -> Unit
) {
    // 各グループのチェック状態を追跡するリスト
    val checkedGroups = remember { mutableStateOf(pastGroup.map { it to false }.toMap()) }
    val showDeleteConfirm = remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                isSheetOpen.value = false
            }
        },
        sheetState = sheetState,
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn {
                    items(pastGroup.size) { index ->
                        val groupItem = pastGroup[index]
                        val isChecked = checkedGroups.value[groupItem] ?: false

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    checkedGroups.value = checkedGroups.value.toMutableMap().apply {
                                        this[groupItem] = checked
                                    }
                                },
                                modifier = Modifier.weight(1f),
                            )
                            Text(
                                text = groupItem.groupName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(2f),
                            )
                            IconButton(onClick = { showDeleteConfirm.value = true }) {
                                Icon(
                                    painter = painterResource(id = TaskMateIcons.Setting),
                                    contentDescription = "削除",
                                    tint = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                                isSheetOpen.value = false
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                    ) {
                        Text("閉じる")
                    }
                    Button(
                        onClick = {
                            val selectedGroups = checkedGroups.value
                                .filterValues { it }
                                .keys
                                .toList()
                            onSave(selectedGroups)
                            scope.launch {
                                sheetState.hide()
                                isSheetOpen.value = false
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                    ) {
                        Text("登録")
                    }
                }
            }
        }
    }
    if (showDeleteConfirm.value) {
        TaskMateAlertDialog(
            title = "確認",
            text = "本当に削除しますか？",
            confirmButton = {
                Button(
                    onClick = {
                        // 削除処理
                        showDeleteConfirm.value = false
                    },
                ) { Text("削除") }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirm.value = false }) {
                    Text("キャンセル")
                }
            },
            isOpenDialog = showDeleteConfirm,
        )
    }
}
