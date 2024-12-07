package com.example.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.model.TaskMateSubject
import com.example.feature.home.SelectListScreenViewModel

@Composable
fun SubjectListCard(
    subject: TaskMateSubject,
    groupName: String,
    rowIndex: Int,
    columnIndex: Int,
    onSuccess: () -> Unit,
    viewModel: SelectListScreenViewModel = viewModel(),
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
            .fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .padding(12.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clickable {
                        onSuccess()
                        viewModel.updateIndex(
                            subjectId = subject.subjectId,
                            columnIndex = columnIndex,
                            rowIndex = rowIndex,
                            onSuccess = { println("成功") },
                            onFailure = { errorMessage ->
                                println(errorMessage)
                            },
                        )
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "＋",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
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
                Text(
                    text = "",
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
