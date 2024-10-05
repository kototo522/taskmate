package com.example.taskmate.ui.task

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(size = 5.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier =
        Modifier
            .padding(start = 8.dp, top = 4.dp, end = 8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(size = 5.dp),
            )
            .fillMaxWidth(),
    ) {
        Column {
            Text(
                text = task.title,
                style =
                TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    letterSpacing = 0.5.sp,
                ),
                modifier = Modifier.padding(4.dp),
            )
            Text(
                text = "aaa",
                modifier = Modifier.padding(start = 20.dp, top = 4.dp),
                style =
                TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    letterSpacing = 0.5.sp,
                ),
            )
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .height(40.dp),
            ) {
                Text(
                    text = "aaa",
                    modifier =
                    Modifier
                        .weight(4f)
                        .padding(10.dp),
                    style =
                    TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Right,
                        letterSpacing = 0.5.sp,
                    ),
                )
            }
        }
    }
}
