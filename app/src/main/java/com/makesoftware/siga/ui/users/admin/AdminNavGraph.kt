package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makesoftware.siga.MainRoutes
import com.makesoftware.siga.StartDestinations
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Subject
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource
import com.makesoftware.siga.data.repositories.CoursesRepository
import com.makesoftware.siga.data.repositories.StudentsRepository
import com.makesoftware.siga.data.repositories.SubjectRepository
import com.makesoftware.siga.data.repositories.TeachersRepository
import com.makesoftware.siga.ui.users.admin.screens.AdminHomeScreen
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminCourseForm
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminStudentForm
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminSubjectForm
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminTeacherForm
import com.makesoftware.siga.ui.users.admin.viewmodels.BasicCrudViewModel


sealed class AdminRoutes {
    companion object {
        const val SUBJECT_FORM = "AdminSubjectForm"
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
    modifier: Modifier = Modifier, navController: NavHostController, paddingValues: PaddingValues,

    courseViewModel: BasicCrudViewModel<Course> = viewModel(
        factory = BasicCrudViewModel.Factory(
            CoursesRepository(RemoteDataSource())
        ) { Course() }, key = "course"
    ),

    teacherViewModel: BasicCrudViewModel<Teacher> = viewModel(
        factory = BasicCrudViewModel.Factory(
            TeachersRepository(RemoteDataSource())
        ) { Teacher() }, key = "teacher"
    ),

    studentViewModel: BasicCrudViewModel<Student> = viewModel(
        factory = BasicCrudViewModel.Factory(
            StudentsRepository(RemoteDataSource())
        ) { Student() }, key = "student"
    ),

    subjectViewModel: BasicCrudViewModel<Subject> = viewModel(
        factory = BasicCrudViewModel.Factory(
            SubjectRepository(RemoteDataSource())
        ) { Subject() }, key = "subject"
    )
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

        studentScreens(studentViewModel, navController)
        courseScreens(courseViewModel, navController)
        teacherScreens(teacherViewModel, navController)
        subjectScreens(subjectViewModel, navController)
    }
}

fun NavGraphBuilder.studentScreens(
    viewModel: BasicCrudViewModel<Student>, navController: NavHostController
) {
    composable(AdminRoutes.STUDENTS) {
        val studentUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminDataViewScreen(columns = Student.columns,
            onAddEntityRequest = { /*TODO*/ },
            fetchResult = studentUiState.fetchResult,
            onItemClick = {
                viewModel.selectEntity(it)
                navController.navigate(AdminRoutes.STUDENT_FORM)
            },
            fetchItems = {
                viewModel.fetchEntity(context)
            })
    }

    composable(AdminRoutes.STUDENT_FORM) {
        val studentUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminStudentForm(student = studentUiState.selectedEntity, updateStudentData = {
            viewModel.updateSelectedEntity(it)
        }, updateStudent = {
            viewModel.updateEntity()
        }, saveStudent = {
            viewModel.saveEntity(context)
        }, isUpdate = studentUiState.isEntityBeingUpdated
        )
    }
}

fun NavGraphBuilder.teacherScreens(
    viewModel: BasicCrudViewModel<Teacher>, navController: NavHostController
) {
    composable(AdminRoutes.TEACHERS) {
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminDataViewScreen(columns = Teacher.columns, onAddEntityRequest = {
            viewModel.clearSelectedEntity()
            navController.navigate(AdminRoutes.TEACHER_FORM)
        }, fetchResult = uiState.fetchResult, fetchItems = {
            viewModel.fetchEntity(context)
        }, onItemClick = {
            viewModel.selectEntity(it)
            navController.navigate(AdminRoutes.TEACHER_FORM)
        })
    }

    composable(AdminRoutes.TEACHER_FORM) {
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminTeacherForm(teacher = uiState.selectedEntity, updateTeacherData = {
            viewModel.updateSelectedEntity(it)
        }, saveTeacherUpdate = {
            viewModel.saveEntity(context)
        }, saveTeacher = {
            viewModel.saveEntity(context)
        }, isUpdate = uiState.isEntityBeingUpdated)
    }
}

fun NavGraphBuilder.courseScreens(
    viewModel: BasicCrudViewModel<Course>, navController: NavHostController
) {
    composable(AdminRoutes.COURSES) {
        val courseUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminDataViewScreen(columns = Course.columns, onAddEntityRequest = {
            viewModel.clearSelectedEntity()
            navController.navigate(AdminRoutes.COURSE_FORM)
        }, fetchResult = courseUiState.fetchResult, fetchItems = {
            viewModel.fetchEntity(context)
        }, onItemClick = {
            viewModel.selectEntity(it)
            navController.navigate(AdminRoutes.COURSE_FORM)
        })
    }

    composable(AdminRoutes.COURSE_FORM) {
        val courseUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminCourseForm(course = courseUiState.selectedEntity, updateCourseData = {
            viewModel.updateSelectedEntity(it)
        }, onSelectSubjectsRequest = {
            // TODO: Navigate to the subjects screen, with the selectable option
        }, saveCourseUpdate = {
            viewModel.updateEntity()
        }, saveCourse = {
            viewModel.saveEntity(context)
        }, isUpdate = courseUiState.isEntityBeingUpdated)
    }
}

fun NavGraphBuilder.subjectScreens(
    viewModel: BasicCrudViewModel<Subject>, navController: NavHostController
) {

    composable(AdminRoutes.SUBJECTS) {
        val subjectUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminDataViewScreen(
            columns = Subject.columns,
            onAddEntityRequest = {
                viewModel.clearSelectedEntity()
                navController.navigate(AdminRoutes.SUBJECT_FORM)
            },
            fetchItems = { viewModel.fetchEntity(context) },
            fetchResult = subjectUiState.fetchResult,
        )
    }

    composable(AdminRoutes.SUBJECT_FORM) {
        val subjectUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminSubjectForm(
            subject = subjectUiState.selectedEntity,
            updateSubjectData = { viewModel.updateSelectedEntity(it) },
            saveSubject = { viewModel.saveEntity(context) },
            updateSubject = { viewModel.updateEntity() },
            isUpdate = subjectUiState.isEntityBeingUpdated,
        )
    }
}