package com.example.core.ui.taskmateComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TaskMateAlertDialog(
    title: String,
    text: String,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    isOpenDialog: MutableState<Boolean>,
) {
    AlertDialog(
        onDismissRequest = { isOpenDialog.value = false },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.W500,
            )
        },
        text = { Text(text = text) },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
    )
}

@Preview
@Composable
private fun PreviewTaskMateAlertDialog() {
    val showDeleteConfirm = remember { mutableStateOf(false) }

    TaskMateAlertDialog(
        title = "確認",
        text = "本当に削除しますか？",
        confirmButton = {
            Button(
                onClick = {},
            ) { Text("削除") }
        },
        dismissButton = {
            Button(onClick = {}) {
                Text("キャンセル")
            }
        },
        isOpenDialog = showDeleteConfirm,
    )
}
