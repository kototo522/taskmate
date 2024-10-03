package com.example.taskmate.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.taskmate.ui.appBar.MainTaskMateAppBar

@Composable
fun MyPageScreen(navToSettingScreen: () -> Unit) {
    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("MyPageScreen", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
