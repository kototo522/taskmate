package com.example.feature.task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject

@Composable
fun SubjectCard(
    subject: TaskMateSubject,
    group: TaskMateGroup?,
    onClick: (TaskMateSubject) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(size = 5.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier =
        Modifier
            .padding(start = 12.dp, top = 8.dp, end = 12.dp)
            .fillMaxWidth()
            .clickable {
                onClick(subject)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = subject.name,
                style =
                TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    letterSpacing = 0.5.sp,
                ),
                modifier = Modifier.padding(vertical = 14.dp),
            )
            if (group != null) {
                Text(
                    text = "グループ名: ${group.groupName}",
                    modifier = Modifier.padding(bottom = 10.dp),
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
