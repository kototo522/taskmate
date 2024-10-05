package com.example.taskmate.ui.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskmate.R
import java.util.*
import com.example.taskmate.ui.appBar.PopBackTaskMateAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(popBackStack: () -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var deadlineDate by remember { mutableStateOf("") }
    var deadlineTime by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(context.getString(R.string.task_public)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(context.getString(R.string.not_remind)) }
    val remindTime = listOf(
        context.getString(R.string.before_1day),
        context.getString(R.string.before_2day)
    )

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(text = context.getString(R.string.add_task), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 課題名の入力欄
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(context.getString(R.string.task_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // 提出場所の入力欄
                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text(context.getString(R.string.task_place)) },
                    modifier = Modifier.fillMaxWidth()
                )

                // 締切日と時間
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = deadlineDate,
                        onValueChange = { deadlineDate = it },
                        label = { Text(context.getString(R.string.deadline_date)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker = true
                            }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "カレンダー")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = deadlineTime,
                        onValueChange = { deadlineTime = it },
                        label = { Text(context.getString(R.string.deadline_time)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showTimePicker = true
                            }) {
                                Icon(painter = painterResource(id = R.drawable.clock), contentDescription = "時計")
                            }
                        }
                    )
                }

                // リマインドの設定
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = selectedText,
                        onValueChange = {},
                        label = { Text(context.getString(R.string.remind)) },
                        readOnly = true,
                        trailingIcon = {
                            IconButton( onClick = { expanded = !expanded } ) {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        remindTime.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Text(context.getString(R.string.publication_range))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = visibility == context.getString(R.string.task_public),
                            onClick = { visibility = context.getString(R.string.task_public) }
                        )
                        Text(context.getString(R.string.task_public))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = visibility == context.getString(R.string.task_private),
                            onClick = { visibility = context.getString(R.string.task_private) }
                        )
                        Text(context.getString(R.string.task_private))
                    }
                }

                // タスク追加ボタン
                Button(
                    onClick = {
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = context.getString(R.string.add_task))
                }
            }
        }

        // 日付選択ダイアログ
        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    deadlineDate = "$year/${month + 1}/$dayOfMonth"
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).apply {
                setOnDismissListener { showDatePicker = false }
            }.show()
        }

        // 時間選択ダイアログ
        if (showTimePicker) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    deadlineTime = String.format("%02d:%02d", hour, minute)
                },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
            ).apply {
                setOnDismissListener { showTimePicker = false }
            }.show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTaskScreen() {
    AddTaskScreen(popBackStack = {})
}
