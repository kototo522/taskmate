package com.example.feature.task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayDate(
    deadlineDate: String,
    modifier: Modifier = Modifier,
) {
    val dateParts = deadlineDate.split("/")
    if (dateParts.size < 3) {
        return
    }
    val year = dateParts[0]
    val month = dateParts[1]
    val day = dateParts[2]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 12.dp),
    ) {
        Box {
            Text(
                text = month,
                modifier = modifier.offset(x = (-10).dp),
                style =
                TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
            Text(
                text = "／",
                modifier =
                modifier
                    .padding(4.dp)
                    .offset(x = 0.dp, y = 2.dp),
                style =
                TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
            Text(
                text = day,
                modifier =
                modifier
                    .padding(4.dp)
                    .offset(x = 14.dp, y = 18.dp),
                style =
                TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
        Text(
            text = year,
            style =
            TextStyle(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 4.dp)
        )
    }
}
