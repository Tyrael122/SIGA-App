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
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminSubjectScreen
import com.makesoftware.siga.ui.users.admin.screens.dataviews.AdminTeacherScreen
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminCourseForm
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminStudentForm
import com.makesoftware.siga.ui.users.admin.viewmodels.CourseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminTeacherForm
import com.makesoftware.siga.ui.users.admin.viewmodels.StudentViewModel
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
    studentViewModel: StudentViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = StartDestinations.ADMIN,
        route = MainRoutes.ADMIN_SPACE,
        modifier = modifier.padding(paddingValues)
    ) {
        composable(AdminRoutes.HOME) {
            AdminHomeScreen()
        }

        subjectScreens()

        courseScreens(courseViewModel, navController)
        teacherScreens(teacherViewModel, navController = navController)
        studentScreens(studentViewModel, navController)
    }
}

fun NavGraphBuilder.studentScreens(
    viewModel: StudentViewModel, navController: NavHostController
) {
    composable(AdminRoutes.STUDENTS) {
        val studentUiState by viewModel.studentUiState.collectAsState()
        val context = LocalContext.current

        AdminDataViewScreen(columns = Student.columns,
            onAddEntityRequest = { /*TODO*/ },
            fetchResult = studentUiState.fetchResult,
            onItemClick = {
                viewModel.selectStudent(it)
                navController.navigate(AdminRoutes.STUDENT_FORM)
            },
            fetchItems = {
                viewModel.fetchStudent(context)
            })
    }

    composable(AdminRoutes.STUDENT_FORM) {
        val studentUiState by viewModel.studentUiState.collectAsState()

        AdminStudentForm(student = studentUiState.selectedStudent, updateStudentData = {
            viewModel.updateSelectedStudent(it)
        }, updateStudent = {
            viewModel.saveStudentUpdate()
        }, saveStudent = {
            viewModel.saveStudent()
        }, isUpdate = studentUiState.selectedStudent.isUpdate
        )
    }
}

fun NavGraphBuilder.teacherScreens(
    viewModel: TeacherViewModel, navController: NavHostController
) {
    composable(AdminRoutes.TEACHERS) {
        val teacherUiState by viewModel.teacherUiState.collectAsState()
        val context = LocalContext.current

        AdminTeacherScreen(onAddTeachersRequest = {
            viewModel.clearSelectedTeacher()
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
        val context = LocalContext.current

        AdminTeacherForm(teacher = teacherUiState.selectedTeacher, updateTeacherData = {
            viewModel.updateSelectedTeacher(it)
        }, saveTeacherUpdate = {
            viewModel.updateTeacher()
        }, saveTeacher = {
            viewModel.saveTeacher(context)
        }, isUpdate = teacherUiState.isTeacherBeingUpdated)
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

fun NavGraphBuilder.subjectScreens() {

    composable(AdminRoutes.SUBJECTS) {
        AdminSubjectScreen()
    }
}