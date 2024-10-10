package com.example.taskmate.ui.task

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmate.ui.task.components.DisplayDate

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(size = 5.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier =
        Modifier
            .padding(start = 12.dp, top = 8.dp, end = 12.dp)
            .fillMaxWidth(),
    ) {
        Row {
            DisplayDate(modifier)
            Column {
                Text(
                    text = task.title,
                    style =
                    TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        letterSpacing = 0.5.sp,
                    ),
                    modifier = Modifier.padding(start = 20.dp, top = 14.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end = 18.dp, bottom = 10.dp),
                ) {
                    Text(
                        text = "技術者倫理哲学",
                        modifier = Modifier
                            .padding(start = 20.dp, top = 4.dp)
                            .weight(1f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            letterSpacing = 0.5.sp,
                        ),
                    )

                    Text(
                        text = "最終編集者: ことりん",
                        modifier = Modifier
                            .padding(start = 20.dp, top = 4.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            letterSpacing = 0.5.sp,
                        ),
                    )
                }

            }
        }
    }
}
