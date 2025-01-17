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
import com.makesoftware.siga.network.NetworkManager
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
            CoursesRepository(RemoteDataSource()), NetworkManager(LocalContext.current)
        ) { Course() }, key = "course"
    ),

    teacherViewModel: BasicCrudViewModel<Teacher> = viewModel(
        factory = BasicCrudViewModel.Factory(
            TeachersRepository(RemoteDataSource()), NetworkManager(LocalContext.current)
        ) { Teacher() }, key = "teacher"
    ),

    studentViewModel: BasicCrudViewModel<Student> = viewModel(
        factory = BasicCrudViewModel.Factory(
            StudentsRepository(RemoteDataSource()), NetworkManager(LocalContext.current)
        ) { Student() }, key = "student"
    ),

    subjectViewModel: BasicCrudViewModel<Subject> = viewModel(
        factory = BasicCrudViewModel.Factory(
            SubjectRepository(RemoteDataSource()), NetworkManager(LocalContext.current)
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

        studentScreens(studentViewModel, subjectViewModel, courseViewModel, navController)
        courseScreens(courseViewModel, subjectViewModel, navController)
        teacherScreens(teacherViewModel, navController)
        subjectScreens(subjectViewModel, navController)
    }
}

fun NavGraphBuilder.studentScreens(
    viewModel: BasicCrudViewModel<Student>,
    subjectsViewModel: BasicCrudViewModel<Subject>,
    courseViewModel: BasicCrudViewModel<Course>,
    navController: NavHostController
) {
    composable(AdminRoutes.STUDENTS) {
        AdminDataViewScreenWrapper(navigateToFormScreen = {
            navController.navigate(AdminRoutes.STUDENT_FORM)
        }, columns = Student.columns, viewModel = viewModel)
    }

    composable(AdminRoutes.STUDENT_FORM) {
        val studentUiState by viewModel.uiState.collectAsState()
        val courseUiState by courseViewModel.uiState.collectAsState()

        val context = LocalContext.current

        AdminStudentForm(
            student = studentUiState.selectedEntity,
            updateStudentData = {
                viewModel.updateSelectedEntity(it)
            },
            updateStudent = {
                viewModel.updateEntity()
            },
            saveStudent = {
                viewModel.saveEntity(context)
            },
            isUpdate = studentUiState.isEntityBeingUpdated,
            onSelectSubjectsRequest = {
                // TODO: Maybe improve this method to accept named parameters, and a way to create a custom callback if necessary. Wait for three repetitions to refactor this.
                subjectsViewModel.setViewAsSelectableWithCallback(studentUiState.selectedEntity.enrolledSubjects) {
                    navController.navigate(AdminRoutes.STUDENT_FORM)

                    viewModel.updateSelectedEntity(
                        studentUiState.selectedEntity.copy(
                            enrolledSubjects = it
                        )
                    )
                }

                navController.navigate(AdminRoutes.SUBJECTS)
            },
            courses = courseUiState.fetchResult.getSucessItemsOrEmptyList(),
        )
    }
}

fun NavGraphBuilder.teacherScreens(
    viewModel: BasicCrudViewModel<Teacher>, navController: NavHostController
) {
    composable(AdminRoutes.TEACHERS) {
        AdminDataViewScreenWrapper(navigateToFormScreen = {
            navController.navigate(AdminRoutes.TEACHER_FORM)
        }, columns = Teacher.columns, viewModel = viewModel)
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
    viewModel: BasicCrudViewModel<Course>,
    subjectsViewModel: BasicCrudViewModel<Subject>,
    navController: NavHostController
) {
    composable(AdminRoutes.COURSES) {
        AdminDataViewScreenWrapper(navigateToFormScreen = {
            navController.navigate(AdminRoutes.COURSE_FORM)
        }, columns = Course.columns, viewModel = viewModel)
    }

    composable(AdminRoutes.COURSE_FORM) {
        val courseUiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        AdminCourseForm(course = courseUiState.selectedEntity, updateCourseData = {
            viewModel.updateSelectedEntity(it)
        }, onSelectSubjectsRequest = {
            subjectsViewModel.setViewAsSelectableWithCallbackBuilder(courseUiState.selectedEntity.subjects,
                navigateToPreviousScreen = {
                    navController.navigate(AdminRoutes.COURSE_FORM)
                },
                navigateToSelectionScreen = {
                    navController.navigate(AdminRoutes.SUBJECTS)
                },
                updateEntitySelection = {
                    viewModel.updateSelectedEntity(
                        courseUiState.selectedEntity.copy(
                            subjects = it
                        )
                    )
                })
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
        AdminDataViewScreenWrapper(navigateToFormScreen = {
            navController.navigate(AdminRoutes.SUBJECT_FORM)
        }, columns = Subject.columns, viewModel = viewModel)
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