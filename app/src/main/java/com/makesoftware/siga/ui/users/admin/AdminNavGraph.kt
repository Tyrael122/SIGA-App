package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makesoftware.siga.MainRoutes
import com.makesoftware.siga.ui.users.admin.screens.AdminCourseScreen
import com.makesoftware.siga.ui.users.admin.screens.AdminHomeScreen
import com.makesoftware.siga.ui.users.admin.screens.AdminStudentScreen
import com.makesoftware.siga.ui.users.admin.screens.AdminSubjectScreen
import com.makesoftware.siga.ui.users.admin.screens.AdminTeacherScreen


class AdminRoutes {
    companion object {
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
        startDestination = AdminRoutes.COURSES,
        route = MainRoutes.ADMIN_SPACE,
        modifier = modifier.padding(paddingValues)
    ) {
        composable(AdminRoutes.HOME) {
            AdminHomeScreen()
        }

        composable(AdminRoutes.COURSES) {
            AdminCourseScreen()
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
}
