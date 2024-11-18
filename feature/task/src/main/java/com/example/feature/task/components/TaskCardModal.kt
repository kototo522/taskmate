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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.TaskMateTask
import com.example.core.ui.taskmateComponents.TaskMateAlertDialog
import com.example.feature.task.TaskViewModel
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
    viewModel: TaskViewModel = viewModel(),
) {
    val isEditing = remember { mutableStateOf(false) }
    val showDeleteConfirm = remember { mutableStateOf(false) }

    // 編集用State
    val titleState = remember { mutableStateOf(task.title) }
    val deadlineDateState = remember { mutableStateOf(task.deadlineDate) }
    val deadlineTimeState = remember { mutableStateOf(task.deadlineTime) }
    val destinationState = remember { mutableStateOf(task.destination) }
    val visibilityState = remember { mutableStateOf(task.visibility) }

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
            if (isEditing.value) {
                // 編集モード
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    TaskEditField(label = "課題名", value = titleState.value) {
                        titleState.value = it
                    }
                    TaskEditField(label = "期限日", value = deadlineDateState.value) {
                        deadlineDateState.value = it
                    }
                    TaskEditField(label = "期限時間", value = deadlineTimeState.value) {
                        deadlineTimeState.value = it
                    }
                    TaskEditField(label = "提出場所", value = destinationState.value) {
                        destinationState.value = it
                    }
                    TaskEditField(label = "公開範囲", value = visibilityState.value) {
                        visibilityState.value = it
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                isEditing.value = false
                            },
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .weight(1f),
                        ) {
                            Text("キャンセル")
                        }
                        Button(
                            onClick = {
                                viewModel.updateTask(
                                    taskId = task.taskId,
                                    title = titleState.value,
                                    deadlineDate = deadlineDateState.value,
                                    deadlineTime = deadlineTimeState.value,
                                    destination = destinationState.value,
                                    visibility = visibilityState.value,
                                    onSuccess = {
                                        isEditing.value = false
                                    },
                                    onFailure = {},
                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .weight(1f),
                        ) {
                            Text("保存")
                        }
                    }
                }
            } else {
                // 詳細表示モード
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
                    TaskDetailText(label = "リマインド", value = "${task.remindTime}")
                    TaskDetailText(label = "公開範囲", value = task.visibility)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                isEditing.value = true
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
    }

    if (showDeleteConfirm.value) {
        TaskMateAlertDialog(
            title = "確認",
            text = "本当に削除しますか？",
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteTask(task.taskId)
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
fun TaskEditField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
        )
        androidx.compose.material3.OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun TaskDetailText(label: String, value: String) {
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 2.dp),
    )
    Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier.padding(bottom = 8.dp),
    )
}
