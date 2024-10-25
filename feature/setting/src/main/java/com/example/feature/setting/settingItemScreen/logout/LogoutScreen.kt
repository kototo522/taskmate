package com.example.feature.setting.settingItemScreen.logout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar

@Composable
fun LogoutScreen(
    popBackStack: () -> Unit,
    logoutViewModel: LogoutViewModel = viewModel(),
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(text = context.getString(TaskMateStrings.Logout), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = context.getString(TaskMateStrings.IsLogout),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Button(
                    onClick = popBackStack,
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Text(context.getString(TaskMateStrings.Cansel))
                }
                Button(
                    onClick = {
                        logoutViewModel.logout(
                            onLogoutSuccess = { popBackStack() },
                            onLogoutFailure = { e -> Log.e("logoutError", e.toString()) },
                        )
                    },
                ) {
                    Text(context.getString(TaskMateStrings.Logout))
                }
            }
        }
    }
}
