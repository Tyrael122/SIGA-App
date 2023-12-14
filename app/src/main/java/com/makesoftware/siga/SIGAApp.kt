package com.makesoftware.siga

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makesoftware.siga.ui.users.admin.AdminSpace
import com.makesoftware.siga.ui.login.LoginFormScreen
import com.makesoftware.siga.ui.login.WelcomeScreen
import com.makesoftware.siga.ui.theme.SIGATheme

class MainRoutes {
    companion object {
        const val LOGIN_FORM = "loginform"
        const val WELCOME_SCREEN = "WelcomeScreen"
        const val ADMIN_SPACE = "AdminSpace"
    }
}

@Composable
fun SIGAApp(modifier: Modifier = Modifier) {
    SIGATheme(useDarkTheme = false) {
        Surface(
            modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = MainRoutes.WELCOME_SCREEN) {
                composable(MainRoutes.WELCOME_SCREEN) {
                    WelcomeScreen(onLogin = {
                        navController.navigate(MainRoutes.LOGIN_FORM)
                    })
                }

                composable(MainRoutes.LOGIN_FORM) {
                    LoginFormScreen(
                        onPasswordReset = {
                            navController.navigate(MainRoutes.ADMIN_SPACE) // TODO: Navigate to password reset
                        },
                        onLogin = {
                            navController.navigate(MainRoutes.ADMIN_SPACE) // TODO: Navigate to each user's space
                        }
                    )
                }

                composable(MainRoutes.ADMIN_SPACE) {
                    AdminSpace(onLogout = {
                        navController.navigate(MainRoutes.WELCOME_SCREEN)
                    })
                }
            }
        }
    }
}

