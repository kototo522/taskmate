package com.example.feature.task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.TaskMateTask
import com.example.core.ui.taskmateComponents.icon.TaskMateIcons

@Composable
fun TaskCard(
    groupName: String,
    subjectName: String,
    task: TaskMateTask,
    isChecked: MutableState<Boolean>,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCardClick()
            },
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
                    Column {
                        Text(
                            text = task.deadlineTime,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                letterSpacing = 0.5.sp,
                            ),
                        )
                        Text(
                            text = task.title,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                letterSpacing = 0.5.sp,
                            ),
                            modifier = Modifier.padding(top = 2.dp, bottom = 8.dp),
                        )
                    }
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = { newValue ->
                            isChecked.value = newValue
                        },
                    )
                }
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
                        Icon(
                            painter = painterResource(TaskMateIcons.Group),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 4.dp),
                        )
                        Icon(
                            painter = painterResource(TaskMateIcons.Notify),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 4.dp),
                        )
                        Icon(
                            painter = painterResource(TaskMateIcons.Share),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 4.dp),
                        )
                    }
                }
            }
        }
    }
}
