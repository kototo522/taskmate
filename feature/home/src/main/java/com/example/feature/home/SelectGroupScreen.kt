package com.example.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateUser
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGroupScreen(
    rowIndex: Int,
    columnIndex: Int,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    popBackStack: () -> Unit,
    navToHomeScreen: () -> Unit,
    onRegisterClick: (String, String) -> Unit,
    viewModel: SelectGroupScreenViewModel = viewModel(),
) {
    var subjectName by remember { mutableStateOf(TextFieldValue("")) }
    var selectedGroupName by remember { mutableStateOf("") }
    var selectedGroupId by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val userGroupIds = remember { mutableStateOf(user?.groupId.orEmpty()) }
    val userGroups by remember(userGroupIds.value) {
        derivedStateOf {
            groups.filter { group ->
                userGroupIds.value.contains(group.groupId)
            }
        }
    }

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = {
                    Text(text = "教科の登録", color = MaterialTheme.colorScheme.secondary)
                },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(
                value = subjectName,
                onValueChange = { subjectName = it },
                label = { Text("教科名") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = selectedGroupName,
                    onValueChange = {},
                    label = { Text("グループ選択") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    userGroups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group.groupName) },
                            onClick = {
                                selectedGroupName = group.groupName
                                selectedGroupId = group.groupId
                                expanded = false
                            },
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (subjectName.text.isNotBlank() && selectedGroupId.isNotBlank()) {
                        onRegisterClick(subjectName.text, selectedGroupId)
                        viewModel.createSubject(
                            subjectName.text,
                            selectedGroupId,
                            rowIndex = rowIndex,
                            columnIndex = columnIndex,
                            onSuccess = {
                                println("成功")
                                navToHomeScreen()
                            },
                            onFailure = { errorMessage ->
                                println("失敗: $errorMessage")
                            },
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            ) {
                Text("登録")
            }
        }
    }
}
