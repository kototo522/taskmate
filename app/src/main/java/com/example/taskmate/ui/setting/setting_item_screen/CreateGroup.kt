package com.example.taskmate.ui.setting.setting_item_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.PopBackTaskMateAppBar


@Composable
fun CreateGroup(popBackStack: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(context.getString(R.string.setting), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Text("Create Group")
        }
    }
}