package com.example.core.model.navigation

import androidx.navigation.NavController
import com.example.core.model.TaskMateSubject
import com.example.core.model.navigation.HomeNavigation.Companion.SETTING_GRAPH_ROUTE

class HomeNavigation(private val navController: NavController) {
    companion object {
        const val HOME_GRAPH_ROUTE = "HomeGraph"
        const val SUBJECT_LIST_ROUTE = "SubjectList"
        const val CREATE_SUBJECT_ROUTE = "CreateSubject"
        const val SETTING_GRAPH_ROUTE = "SettingGraph"
    }
    val navToSubjectListScreen: (Int, Int?) -> Unit = { rowIndex, columnIndex ->
        navController.navigate("$SUBJECT_LIST_ROUTE/$rowIndex/$columnIndex")
    }
    val navToCreateSubjectScreen: (Int, Int?) -> Unit = { rowIndex, columnIndex ->
        navController.navigate("$CREATE_SUBJECT_ROUTE/$rowIndex/$columnIndex")
    }
    val navToHomeScreen: () -> Unit = { navController.navigate(HOME_GRAPH_ROUTE) }
    val navToSettingScreen: () -> Unit = { navController.navigate(SETTING_GRAPH_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}

class TaskNavigation(private val navController: NavController) {
    companion object {
        const val TASK_GRAPH_ROUTE = "TaskGraph"
        const val SELECT_SUBJECT_ROUTE = "SelectSubjectScreen"
        const val ADD_TASK_ROUTE = "AddTaskScreen"
    }
    val navToTaskScreen: () -> Unit = { navController.navigate(BottomNavBarItems.Task.route) }
    val navToSelectSubjectScreen: () -> Unit = { navController.navigate(SELECT_SUBJECT_ROUTE) }
    val navToAddTaskScreen: (TaskMateSubject) -> Unit = { subject ->
        navController.navigate("$ADD_TASK_ROUTE/${subject.subjectId}")
    }
    val navToSettingScreen: () -> Unit = { navController.navigate(SETTING_GRAPH_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}

class MyPageNavigation(private val navController: NavController)

class SettingNavigation(private val navController: NavController) {
    companion object {
        const val SETTING_GRAPH_ROUTE = "SettingGraph"
        const val SETTING_ROUTE = "SettingScreen"
        const val CREATE_GROUP_ROUTE = "CreateGroup"
        const val JOIN_GROUP_ROUTE = "JoinGroup"
        const val LOGOUT_ROUTE = "LogOut"
    }
    val navToCreateGroupScreen: () -> Unit = { navController.navigate(CREATE_GROUP_ROUTE) }
    val navToJoinGroupScreen: () -> Unit = { navController.navigate(JOIN_GROUP_ROUTE) }
    val navToLogoutScreen: () -> Unit = { navController.navigate(LOGOUT_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}

class AuthNavigation(private val navController: NavController) {
    companion object {
        const val AUTH_GRAPH_ROUTE = "AuthGraph"
        const val FIRST_AUTH_ROUTE = "FirstAuthScreen"
        const val LOGIN_ROUTE = "LoginScreen"
        const val SIGNUP_ROUTE = "SignUpScreen"
    }
    val navToHomeScreen: () -> Unit = { navController.navigate(BottomNavBarItems.Home.route) }
    val navToLoginScreen: () -> Unit = { navController.navigate(LOGIN_ROUTE) }
    val navToSignUpScreen: () -> Unit = { navController.navigate(SIGNUP_ROUTE) }
    val popBackStack: () -> Unit = { navController.popBackStack() }
}
