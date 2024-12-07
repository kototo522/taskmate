package com.example.feature.mypage.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.model.TaskMateGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailGroupModal(
    group: MutableState<TaskMateGroup?>,
    scope: CoroutineScope,
    sheetState: SheetState,
    isSheetOpen: MutableState<Boolean>,
) {
    val isQROpen = remember { mutableStateOf(false) }
    val QRsheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

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
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
            ) {
                group.value?.let { groupDetails ->
                    GroupDetailText(label = "グループ名", value = groupDetails.groupName)
                    GroupDetailText(label = "パスワード", value = groupDetails.password)
                    GroupDetailText(label = "作成日", value = formatDate(groupDetails.createdAt))
                    GroupDetailText(label = "最終更新日", value = formatDate(groupDetails.lastUpdatedAt))
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = {
                            isQROpen.value = true
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f),
                    ) {
                        Text("グループQRを表示する")
                    }
                }
            }
        }
    }
    if (isQROpen.value) {
        ShowQRModal(
            data = "https://example.com/group?groupId={groupId}&password={password}",
            scope = coroutineScope,
            sheetState = QRsheetState,
            isSheetOpen = isQROpen,
        )
    }
}

@Composable
fun GroupDetailText(label: String, value: String) {
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 4.dp),
    )
    Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier.padding(bottom = 12.dp),
    )
}

private fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.JAPAN)
    return formatter.format(date)
}
