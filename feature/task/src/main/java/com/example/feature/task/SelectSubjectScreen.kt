import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.model.TaskmateSubject
import com.example.feature.task.components.SubjectCard

@Composable
fun SelectSubjectScreen(
    navToAddTaskScreen: (TaskmateSubject) -> Unit,
    popBackStack: () -> Unit,
) {
    val context = LocalContext.current
    val subjects = listOf(
        TaskmateSubject("数学", Color(0xFF42A5F5)), // 青
        TaskmateSubject("英語", Color(0xFF66BB6A)), // 緑
        TaskmateSubject("歴史", Color(0xFFFFC107)), // 黄
    )

    Scaffold(
        topBar = {
//            PopBackTaskMateAppBar(
//                title = { Text(context.getString(R.string.add_task), color = MaterialTheme.colorScheme.secondary) },
//                popBackScreen = popBackStack,
//                modifier = Modifier,
//            )
        },
        containerColor = MaterialTheme.colorScheme.background, // 背景色をテーマに合わせる
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                items(subjects) { subject ->
                    SubjectCard(subject = subject, onClick = { navToAddTaskScreen(subject) })
                }
            }
        }
    }
}
