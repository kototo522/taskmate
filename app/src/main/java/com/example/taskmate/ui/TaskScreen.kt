package com.example.taskmate.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.MainTaskMateAppBar

@Composable
fun TaskScreen(
    navToSettingScreen: () -> Unit,
    navToAddTaskScreen: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            MainTaskMateAppBar(navToSettingScreen, Modifier)
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                FloatingActionButton(
                    onClick = navToAddTaskScreen,
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Text(text = context.getString(R.string.add_icon), fontSize = 24.sp)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("TaskScreen", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
