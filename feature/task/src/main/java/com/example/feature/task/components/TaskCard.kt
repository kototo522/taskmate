package com.example.feature.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.TaskMateTask

@Composable
fun TaskCard(
    groupName: String,
    subjectName: String,
    task: TaskMateTask,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DisplayDate(
                deadlineDate = task.deadlineDate,
                modifier = Modifier
                    .padding(end = 4.dp),
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = task.deadlineTime,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            letterSpacing = 0.5.sp,
                        ),
                    )
                    // 右寄せ要素
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "edit",
                        )
                        Text(
                            text = "Checkbox",
                        )
                    }
                }

                Text(
                    text = task.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        letterSpacing = 0.5.sp,
                    ),
                    modifier = Modifier.padding(vertical = 4.dp),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "$subjectName / $groupName",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                        modifier = Modifier.weight(2f),
                    )
                    // 右寄せ要素
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "isShare",
                        )
                        Text(
                            text = "Notify",
                        )
                        Text(
                            text = "Share",
                        )
                    }
                }
            }
        }
    }
}
