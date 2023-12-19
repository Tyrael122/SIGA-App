package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makesoftware.siga.MainRoutes
import com.makesoftware.siga.StartDestinations
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminCourseScreen
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminHomeScreen
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminStudentScreen
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminSubjectScreen
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminTeacherScreen
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminCourseForm
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminStudentForm
import com.makesoftware.siga.ui.users.admin.viewmodels.AdminViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


sealed class AdminRoutes {
    companion object {
        const val COURSE_FORM = "AdminCourseForm"
        const val STUDENT_FORM = "AdminStudentForm"
        const val COURSES = "Cursos"
        const val SUBJECTS = "Materias"
        const val STUDENTS = "Alunos"
        const val TEACHERS = "Professores"
        const val HOME = "AdminHome"
    }
}

@Composable
fun AdminNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: AdminViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = StartDestinations.ADMIN,
        route = MainRoutes.ADMIN_SPACE,
        modifier = modifier.padding(paddingValues)
    ) {
        adminDataview(viewModel, navController)

        courseScreens(viewModel, navController)

        // TODO: Create the student form functions.
        composable(AdminRoutes.STUDENT_FORM) {
            AdminStudentForm(onCommitRequest = {}) {}
        }
    }
}

fun NavGraphBuilder.courseScreens(
    viewModel: AdminViewModel, navController: NavHostController
) {
    composable(AdminRoutes.COURSES) {
        val courseUiState by viewModel.courseUiState.collectAsState()
        val context = LocalContext.current

        AdminCourseScreen(onAddCourseRequest = {
            viewModel.clearCourseForm()
            navController.navigate(AdminRoutes.COURSE_FORM)
        }, fetchResult = courseUiState.fetchResult, fetchCourses = {
            viewModel.fetchCourses(context)
        }, onSelectCourse = {
            viewModel.selectCourse(it)
            navController.navigate(AdminRoutes.COURSE_FORM)
        })
    }

    // TODO: Pass the currently selected course and lock the screen for view only, until user presses the edit button.
    composable(AdminRoutes.COURSE_FORM) {
        val courseUiState by viewModel.courseUiState.collectAsState()

        AdminCourseForm(course = courseUiState.selectedCourse, updateCourse = {
            viewModel.updateSelectedCourse(it)
        }, onSelectSubjectsRequest = {
            // TODO: Navigate to the subjects screen, with the selectable option
        }, onCommitRequest = { incomingFormState ->
            viewModel.commitCourseFormState(incomingFormState)
            // TODO: Show a toast message saying that the course was saved
        }, formState = courseUiState.formState
        )
    }
}

fun NavGraphBuilder.adminDataview(
    viewModel: AdminViewModel, navController: NavHostController
) {
    composable(AdminRoutes.HOME) {
        AdminHomeScreen()
    }

    composable(AdminRoutes.SUBJECTS) {
        AdminSubjectScreen()
    }

    composable(AdminRoutes.TEACHERS) {
        val teacherUiState by viewModel.teacherUiState.collectAsState()
        val context = LocalContext.current

        AdminTeacherScreen(onAddTeachers = {},
            fetchResult = teacherUiState.fetchResult,
            fetchTeachers = {
                viewModel.fetchTeachers(context)
            })
    }

    composable(AdminRoutes.STUDENTS) {
        AdminStudentScreen(onAddStudent = {
            navController.navigate(AdminRoutes.STUDENT_FORM)
        })
    }
}