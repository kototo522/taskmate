package com.example.feature.mypage

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Tag
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateUser
import com.example.core.model.string.TaskMateStrings
import com.example.core.ui.taskmateComponents.appBar.MainTaskMateAppBar
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons
import com.example.feature.mypage.components.EditTagCardModal
import com.example.feature.mypage.components.TagCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    navToSettingScreen: () -> Unit,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isSheetOpen = remember { mutableStateOf(false) }

    val userGroupIds = user?.groupId
    val userGroups = userGroupIds?.let { ids ->
        groups.filter { group ->
            ids.contains(group.groupId)
        }.map { group ->
            group.groupName
        }
    } ?: emptyList()

    val tags = listOf(
        Tag("タグ1", Color(0xFF42A5F5)),
        Tag("タグ2", Color(0xFF66BB6A)),
    )

    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 20.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .size(140.dp),
                    ) {
                        Text(
                            text = context.getString(TaskMateStrings.AddIcon),
                            fontSize = 38.sp,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable(
                                    indication = rememberRipple(
                                        radius = 20.dp, // リップル半径
                                        bounded = false, // 範囲をビューに制限しない（丸く広がる）
                                    ),
                                    interactionSource = remember { MutableInteractionSource() },
                                ) {},
                        )
                        Icon(
                            painter = painterResource(id = TaskMateIcons.Account),
                            contentDescription = null,
                            modifier = Modifier
                                .height(140.dp)
                                .width(140.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "ユーザ名",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    Text(
                        text = user?.userName ?: "ユーザーネーム",
                        fontSize = 28.sp,
                        fontWeight = FontWeight(700),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp),
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "所属グループ",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(5f),
                        )
                        // ボタンのリップルがズレるためBoxでかこむ
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                        ) {
                            Text(
                                text = context.getString(TaskMateStrings.AddIcon),
                                fontSize = 24.sp,
                                modifier = Modifier.clickable(
                                    indication = rememberRipple(
                                        radius = 20.dp, // リップル半径
                                        bounded = false, // 範囲をビューに制限しない（丸く広がる）
                                    ),
                                    interactionSource = remember { MutableInteractionSource() },
                                ) {
                                    scope.launch {
                                        isSheetOpen.value = true
                                        sheetState.show()
                                    }
                                },
                            )
                        }
                    }

                    LazyRow(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        items(userGroups.size) { index ->
                            TagCard(userGroups[index])
                        }
                    }
                }
            }
        }
        if (isSheetOpen.value) {
            EditTagCardModal(tags, scope, sheetState, isSheetOpen)
        }
    }
}
