package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makesoftware.siga.MainRoutes
import com.makesoftware.siga.ui.users.admin.screens.dataview.AdminCourseScreen
import com.makesoftware.siga.ui.users.admin.screens.dataview.AdminHomeScreen
import com.makesoftware.siga.ui.users.admin.screens.dataview.AdminStudentScreen
import com.makesoftware.siga.ui.users.admin.screens.dataview.AdminSubjectScreen
import com.makesoftware.siga.ui.users.admin.screens.dataview.AdminTeacherScreen
import com.makesoftware.siga.ui.users.admin.screens.forms.AdminCourseForm


class AdminRoutes {
    companion object {
        const val COURSE_FORM = "AdminCourseForm"
        const val COURSES = "Cursos"
        const val SUBJECTS = "Materias"
        const val STUDENTS = "Alunos"
        const val TEACHERS = "Professores"
        const val HOME = "AdminHome"
    }
}

@Composable
fun AdminNavGraph(
    navController: NavHostController, paddingValues: PaddingValues, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AdminRoutes.COURSE_FORM,
        route = MainRoutes.ADMIN_SPACE,
        modifier = modifier.padding(paddingValues)
    ) {
        adminDataview(navController)

        composable(AdminRoutes.COURSE_FORM) {
            AdminCourseForm(onSelectSubjectsRequest = {
                // TODO: Navigate to the subjects screen, with the selectable option
            }, onCommitRequest = {
                // TODO: Actually save the course
                // TODO: Show a toast message saying that the course was saved
            }, commitButtonText = "Salvar"
            )
        }
    }
}

fun NavGraphBuilder.adminDataview(
    navController: NavHostController
) {
    composable(AdminRoutes.HOME) {
        AdminHomeScreen()
    }

    composable(AdminRoutes.COURSES) {
        AdminCourseScreen(onAddCourse = {
            navController.navigate(AdminRoutes.COURSE_FORM)
        })
    }

    composable(AdminRoutes.SUBJECTS) {
        AdminSubjectScreen()
    }

    composable(AdminRoutes.TEACHERS) {
        AdminTeacherScreen()
    }

    composable(AdminRoutes.STUDENTS) {
        AdminStudentScreen()
    }
}