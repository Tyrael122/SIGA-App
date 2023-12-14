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


class AdminRoutes {
    companion object {
        const val COURSES = "Cursos"
        const val SUBJECTS = "Materias"
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
    }
}
