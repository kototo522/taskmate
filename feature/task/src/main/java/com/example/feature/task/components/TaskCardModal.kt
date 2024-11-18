package com.example.feature.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateTask
import com.example.core.ui.taskmateComponents.TaskMateAlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCardModal(
    scope: CoroutineScope,
    sheetState: SheetState,
    isSheetOpen: MutableState<Boolean>,
    task: TaskMateTask,
    groupName: String,
    subjectName: String,
    onSave: (List<TaskMateGroup>) -> Unit,
) {
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
            ) {
                TaskDetailText(label = "課題名", value = task.title)
                TaskDetailText(label = "教科名/ グループ名", value = "$subjectName/ $groupName")
                TaskDetailText(label = "期限", value = "${task.deadlineDate} ${task.deadlineTime}")
                TaskDetailText(label = "提出場所", value = task.destination)
                TaskDetailText(label = "リマインド", value = task.remindTime)
                TaskDetailText(label = "公開範囲", value = task.visibility)

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
                        Text("編集")
                    }
                    Button(
                        onClick = {
                            showDeleteConfirm.value = true
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                    ) {
                        Text("削除")
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
                        // TODO: 削除処理
                        showDeleteConfirm.value = false
                        scope.launch {
                            sheetState.hide()
                            isSheetOpen.value = false
                        }
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

@Composable
fun TaskDetailText(label: String, value: String) {
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 4.dp),
    )
    Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier.padding(bottom = 12.dp),
    )
}
