package com.example.taskmate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.MainTaskMateAppBar

data class Tag(
    val name: String,
    val color: Color,
)

@Composable
fun MyPageScreen(navToSettingScreen: () -> Unit) {
    val context = LocalContext.current
    val tags = listOf(
        Tag("タグ1", Color(0xFF42A5F5)),
        Tag("タグ2", Color(0xFF66BB6A))
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
            Box(
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.TopEnd,
                        modifier = Modifier
                            .size(140.dp)
                    ) {
                        Text(
                            text = context.getString(R.string.add_icon),
                            fontSize = 28.sp,
                            modifier = Modifier
                                .clickable { }
                                .padding(8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.account),
                            contentDescription = null,
                            modifier = Modifier
                                .height(140.dp)
                                .width(140.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "ユーザ名",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Text(
                        text = "ユーザネーム",
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "所属グループ",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(5f)
                        )
                        Text(
                            text = context.getString(R.string.add_icon),
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clickable {}
                                .weight(1f)
                                .padding(4.dp)
                        )
                    }

                    LazyRow(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(tags.size) { tag ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = tags[tag].color),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .wrapContentSize()
                            ) {
                                Text(
                                    text = tags[tag].name,
                                    fontSize = 20.sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
