package com.example.feature.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.TaskMateSubject
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    userId: String?,
    subject: TaskMateSubject?,
    navToTaskScreen: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var deadlineDate by remember { mutableStateOf("") }
    var deadlineTime by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(context.getString(TaskMateStrings.TaskPublic)) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val isError = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = {
                    subject?.let {
                        Text(text = it.name, color = MaterialTheme.colorScheme.secondary)
                    }
                },
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
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // 課題名の入力欄
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(context.getString(TaskMateStrings.TaskName)) },
                    modifier = Modifier.fillMaxWidth(),
                )

                // 提出場所の入力欄
                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text(context.getString(TaskMateStrings.TaskPlace)) },
                    modifier = Modifier.fillMaxWidth(),
                )

                // 締切日と時間
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    OutlinedTextField(
                        value = deadlineDate,
                        onValueChange = { deadlineDate = it },
                        label = { Text(context.getString(TaskMateStrings.DeadlineDate)) },
                        modifier = Modifier.weight(1f),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker = true
                            }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = "カレンダー")
                            }
                        },
                    )

                    Spacer(Modifier.width(16.dp))

                    OutlinedTextField(
                        value = deadlineTime,
                        onValueChange = { deadlineTime = it },
                        label = { Text(context.getString(TaskMateStrings.DeadlineTime)) },
                        modifier = Modifier
                            .weight(1f),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showTimePicker = true
                            }) {
                                Icon(painter = painterResource(id = TaskMateIcons.Clock), contentDescription = "時計")
                            }
                        },
                    )
                }

                Text(context.getString(TaskMateStrings.PublicationRange))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = visibility == context.getString(TaskMateStrings.TaskPublic),
                            onClick = { visibility = context.getString(TaskMateStrings.TaskPublic) },
                        )
                        Text(context.getString(TaskMateStrings.TaskPublic))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = visibility == context.getString(TaskMateStrings.TaskPrivate),
                            onClick = { visibility = context.getString(TaskMateStrings.TaskPrivate) },
                        )
                        Text(context.getString(TaskMateStrings.TaskPrivate))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (isError.value) {
                        Text(
                            text = "入力漏れがあります",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp).weight(1f),
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    // タスク追加ボタン
                    Button(
                        onClick = {
                            if (title == "" || deadlineDate == "" || deadlineTime == "" || destination == "") {
                                isError.value = true
                            } else {
                                isError.value = false
                                viewModel.createTask(
                                    userId = userId!!,
                                    groupId = subject!!.groupId,
                                    subjectId = subject.subjectId,
                                    title = title,
                                    destination = destination,
                                    deadlineDate = deadlineDate,
                                    deadlineTime = deadlineTime,
                                    visibility = visibility,
                                    onSuccess = {
                                        navToTaskScreen()
                                    },
                                    onFailure = { e -> println(e) },
                                )
                            }
                        },
                    ) {
                        Text(text = context.getString(TaskMateStrings.AddTask))
                    }
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
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
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
                true,
            ).apply {
                setOnDismissListener { showTimePicker = false }
            }.show()
        }
    }
}
