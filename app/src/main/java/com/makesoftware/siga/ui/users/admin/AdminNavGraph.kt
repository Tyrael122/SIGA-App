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
import com.makesoftware.siga.ui.users.admin.viewmodels.CourseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navigation
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminTeacherForm
import com.makesoftware.siga.ui.users.admin.viewmodels.TeacherViewModel


sealed class AdminRoutes {
    companion object {
        const val TEACHER_FORM = "AdminTeacherForm"
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
    courseViewModel: CourseViewModel = viewModel(),
    teacherViewModel: TeacherViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = StartDestinations.ADMIN,
        route = MainRoutes.ADMIN_SPACE,
        modifier = modifier.padding(paddingValues)
    ) {
        adminDataview(navController)

        courseScreens(courseViewModel, navController)

        teacherScreens(teacherViewModel, navController = navController)

        // TODO: Create the student form functions.
        composable(AdminRoutes.STUDENT_FORM) {
            AdminStudentForm {}
        }
    }
}

fun NavGraphBuilder.teacherScreens(
    viewModel: TeacherViewModel, navController: NavHostController
) {
    composable(AdminRoutes.TEACHERS) {
        val teacherUiState by viewModel.teacherUiState.collectAsState()
        val context = LocalContext.current

        AdminTeacherScreen(onAddTeachers = {
            navController.navigate(AdminRoutes.TEACHER_FORM)
        }, fetchResult = teacherUiState.fetchResult, fetchTeachers = {
            viewModel.fetchTeachers(context)
        }, onSelectedTeacher = {
            viewModel.selectTeacher(it)
            navController.navigate(AdminRoutes.TEACHER_FORM)
        })
    }

    composable(AdminRoutes.TEACHER_FORM) {
        val teacherUiState by viewModel.teacherUiState.collectAsState()

        AdminTeacherForm(teacher = teacherUiState.selectedTeacher, updateTeacherData = {
            viewModel.updateSelectedTeacher(it)
        }, saveTeacherUpdate = {
            viewModel.saveTeacherUpdate()
        }, saveTeacher = {
            viewModel.saveTeacher()
        }, isUpdate = teacherUiState.selectedTeacher.isUpdate)
    }
}

fun NavGraphBuilder.courseScreens(
    viewModel: CourseViewModel, navController: NavHostController
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


    composable(AdminRoutes.COURSE_FORM) {
        val courseUiState by viewModel.courseUiState.collectAsState()

        AdminCourseForm(course = courseUiState.selectedCourse, updateCourseData = {
            viewModel.updateSelectedCourse(it)
        }, onSelectSubjectsRequest = {
            // TODO: Navigate to the subjects screen, with the selectable option
        }, saveCourseUpdate = {
            viewModel.saveCourseUpdate()
        }, saveCourse = {
            viewModel.saveCourse()
        }, isUpdate = courseUiState.selectedCourse.isUpdate)
    }
}

fun NavGraphBuilder.adminDataview(
    navController: NavHostController
) {
    composable(AdminRoutes.HOME) {
        AdminHomeScreen()
    }

    composable(AdminRoutes.SUBJECTS) {
        AdminSubjectScreen()
    }

    composable(AdminRoutes.STUDENTS) {
        AdminStudentScreen(onAddStudent = {
            navController.navigate(AdminRoutes.STUDENT_FORM)
        })
    }
}