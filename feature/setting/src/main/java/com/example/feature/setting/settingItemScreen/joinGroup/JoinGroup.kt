package com.example.feature.setting.settingItemScreen.joinGroup

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
fun JoinGroup(
    popBackStack: () -> Unit,
    joinUserId: String,
    joinGroupViewModel: JoinGroupViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text("グループ参加", color = MaterialTheme.colorScheme.secondary) },
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
                    text = "既存のグループに参加する",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )

                OutlinedTextField(
                    value = joinGroupViewModel.groupName,
                    onValueChange = { joinGroupViewModel.groupName = it },
                    label = { Text("グループ名") },
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = joinGroupViewModel.password,
                    onValueChange = { joinGroupViewModel.password = it },
                    label = { Text("公開パスワード") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        joinGroupViewModel.joinGroup(
                            userId = joinUserId,
                            onSuccess = { popBackStack() },
                            onFailure = { e -> println("Error: $e") },
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("参加する", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
