package com.example.taskmate.ui.taskmateComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun TaskmateAlertDialog(
    title: String,
    text: String,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    isOpenDialog: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { isOpenDialog.value = false },
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
    )
}