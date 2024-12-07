package com.example.feature.setting.settingItemScreen.joinGroup

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.ui.taskmateComponents.appBar.PopBackTaskMateAppBar
import com.google.zxing.integration.android.IntentIntegrator

@Composable
fun JoinGroup(
    popBackStack: () -> Unit,
    joinUserId: String,
    joinGroupViewModel: JoinGroupViewModel = viewModel(),
) {
    val context = LocalContext.current
    val activity = remember { context as? Activity }
    val scanQrLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        val scannedData = IntentIntegrator.parseActivityResult(
            result.resultCode,
            result.data,
        )
        if (scannedData.contents != null) {
            val scannedUrl = scannedData.contents
            val uri = Uri.parse(scannedUrl)
            val groupId = uri.getQueryParameter("groupID")
            val password = uri.getQueryParameter("password")

            if (groupId != null && password != null) {
                joinGroupViewModel.groupName = groupId
                joinGroupViewModel.password = password
            } else {
                println("URLから必要な情報を取得できませんでした")
            }
        } else {
            println("QRコードが読み取れませんでした")
        }
    }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End, // 右寄せ
                ) {
                    Button(
                        onClick = {
                            activity?.let {
                                val integrator = IntentIntegrator(it)
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                                integrator.setPrompt("QRコードをスキャンしてください")
                                integrator.setCameraId(0) // 後ろのカメラ
                                integrator.setBeepEnabled(false)
                                integrator.setOrientationLocked(false)
                                scanQrLauncher.launch(integrator.createScanIntent())
                            }
                        },
                    ) {
                        Text("カメラで参加")
                    }
                }

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
