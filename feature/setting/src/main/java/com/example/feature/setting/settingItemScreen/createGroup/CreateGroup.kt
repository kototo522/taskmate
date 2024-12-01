package com.example.feature.setting.settingItemScreen.createGroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar

@Composable
fun CreateGroup(
    popBackStack: () -> Unit,
    createUserId: String,
    createGroupViewModel: CreateGroupViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text("グループ作成", color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
            ) {
                Text(
                    text = "新しくグループを作成する",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                OutlinedTextField(
                    value = createGroupViewModel.groupName,
                    onValueChange = { createGroupViewModel.groupName = it },
                    label = { Text("グループ名") },
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = createGroupViewModel.password,
                    onValueChange = { createGroupViewModel.password = it },
                    label = { Text("公開パスワード") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        createGroupViewModel.createGroup(
                            createUserId = createUserId,
                            onSuccess = { popBackStack() },
                            onFailure = { e -> println("Error: $e") },
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("登録する", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
