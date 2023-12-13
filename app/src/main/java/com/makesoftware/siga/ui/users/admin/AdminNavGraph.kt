package com.makesoftware.siga.ui.users.admin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makesoftware.siga.ui.users.admin.screens.AdminHomeScreen



class AdminRoutes {
    companion object {
        const val CURSOS = "Cursos"
        const val MATERIAS = "Materias"
        const val HOME = "AdminHome"
    }
}

@Composable
fun AdminNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AdminRoutes.HOME) {
        composable(AdminRoutes.HOME) {
            AdminHomeScreen()
        }
    }
}
