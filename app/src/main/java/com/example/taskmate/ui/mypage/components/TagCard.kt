package com.example.taskmate.ui.mypage.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.Tag

@Composable
fun TagCard(tag: Tag) {
    Card(
        colors = CardDefaults.cardColors(containerColor = tag.color.copy(alpha = 0.7f)),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .wrapContentSize(),
    ) {
        Text(
            text = "#${tag.name}",
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp),
        )
    }
}
