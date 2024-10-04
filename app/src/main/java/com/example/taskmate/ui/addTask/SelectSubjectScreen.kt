import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmate.R
import com.example.taskmate.ui.appBar.PopBackTaskMateAppBar

data class Subject(
    val name: String,
    val color: Color,
)

@Composable
fun SelectSubjectScreen(
    navToAddTaskScreen: (Subject) -> Unit,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val subjects = listOf(
        Subject("数学", Color(0xFF42A5F5)), // 青
        Subject("英語", Color(0xFF66BB6A)), // 緑
        Subject("歴史", Color(0xFFFFC107)), // 黄
    )

    Scaffold(
        topBar = {
            PopBackTaskMateAppBar(
                title = { Text(context.getString(R.string.add_task), color = MaterialTheme.colorScheme.secondary) },
                popBackScreen = popBackStack,
                modifier = Modifier,
            )
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
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(subjects) { subject ->
                    SubjectCard(subject = subject, onClick = { navToAddTaskScreen(subject) })
                }
            }
        }
    }
}

@Composable
fun SubjectCard(subject: Subject, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(2.dp, subject.color, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.book),
                contentDescription = null,
                tint = subject.color,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = subject.name,
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
