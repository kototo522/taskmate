package com.example.feature.task.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.core.model.TaskMateGroup
import com.example.core.model.TaskMateSubject
import com.example.core.model.TaskMateUser
import com.example.core.model.navigation.BottomNavBarItems
import com.example.core.model.navigation.TaskNavigation
import com.example.core.model.navigation.TaskNavigation.Companion.ADD_TASK_ROUTE
import com.example.core.model.navigation.TaskNavigation.Companion.SELECT_SUBJECT_ROUTE
import com.example.core.model.navigation.TaskNavigation.Companion.TASK_GRAPH_ROUTE
import com.example.feature.task.AddTaskScreen
import com.example.feature.task.SelectSubjectScreen
import com.example.feature.task.TaskScreen

fun NavGraphBuilder.taskNavGraph(
    TaskController: TaskNavigation,
    users: List<TaskMateUser>,
    user: TaskMateUser?,
    groups: List<TaskMateGroup>,
    subjects: List<TaskMateSubject>,
) {
    val navToAddTaskScreen = TaskController.navToAddTaskScreen
    val navToSelectSubjectScreen = TaskController.navToSelectSubjectScreen
    val navToSettingScreen = TaskController.navToSettingScreen
    val popBackStack: () -> Unit = { TaskController.popBackStack() }

    navigation(
        startDestination = BottomNavBarItems.Task.route,
        route = TASK_GRAPH_ROUTE,
    ) {
        composable(route = BottomNavBarItems.Task.route) {
            TaskScreen(
                users,
                groups,
                subjects,
                navToSettingScreen,
                navToSelectSubjectScreen)
        }
        composable(route = SELECT_SUBJECT_ROUTE) {
            SelectSubjectScreen(user, groups, subjects, navToAddTaskScreen, popBackStack)
        }
        composable(
            route = "$ADD_TASK_ROUTE/{subjectId}/{groupId}",
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
                navArgument("groupId") { type = NavType.StringType; defaultValue = "null" },
            ),
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId")
            val groupId = backStackEntry.arguments?.getString("groupId")
            val selectedSubject = subjects.find { it.subjectId == subjectId }
            val selectedGroup = groups.find { it.groupId == groupId }
            if (user != null) {
                AddTaskScreen(
                    userId = user.userId,
                    subject = selectedSubject,
                    group = selectedGroup,
                    navToTaskScreen = { TaskController.navToTaskScreen },
                    popBackStack = popBackStack,
                )
            }
        }
    }
}
