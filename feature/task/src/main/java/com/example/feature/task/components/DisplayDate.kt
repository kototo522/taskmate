package com.example.feature.task.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayDate(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(100.dp)
            .padding(vertical = 4.dp, horizontal = 18.dp),
    ) {
        Text(
            text = "2024",
            style =
            TextStyle(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Box {
            Text(
                text = "11",
                modifier = Modifier.offset(x = (-10).dp),
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
                Modifier
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
                text = "10",
                modifier =
                Modifier
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
    }
}
