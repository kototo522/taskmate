package com.example.feature.task.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                navToSettingScreen,
                navToSelectSubjectScreen,
            )
        }
        composable(route = SELECT_SUBJECT_ROUTE) {
            SelectSubjectScreen(navToAddTaskScreen, popBackStack)
        }
        composable(
            route = "$ADD_TASK_ROUTE/{subjectId}",
            arguments = listOf(
                navArgument("subjectId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getString("subjectId")
            val selectedSubject = remember(subjectId) {
                subjects.find { it.subjectId == subjectId }
            }

            if (user != null) {
                AddTaskScreen(
                    userId = user.userId,
                    subject = selectedSubject,
                    navToTaskScreen = { TaskController.navToTaskScreen },
                    popBackStack = popBackStack,
                )
            }
        }
    }
}
