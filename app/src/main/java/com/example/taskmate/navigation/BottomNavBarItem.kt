import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBarItems(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home : BottomNavBarItems(route = "home", title = "Home", icon = Icons.Default.DateRange)
    object Task : BottomNavBarItems(route = "task", title = "Task", icon = Icons.AutoMirrored.Filled.List)
    object MyPage : BottomNavBarItems(route = "my_page", title = "MyPage", icon = Icons.Default.AccountCircle)
}
