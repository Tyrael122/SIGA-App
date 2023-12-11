package com.makesoftware.siga

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makesoftware.siga.ui.login.LoginFormScreen
import com.makesoftware.siga.ui.login.LoginScreen
import com.makesoftware.siga.ui.theme.SIGATheme

class Routes {
    companion object {
        const val LOGIN_FORM = "loginform"
        const val LOGIN = "login"
    }
}

@Composable
fun SIGAApp(modifier: Modifier = Modifier) {
    SIGATheme(useDarkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.LOGIN_FORM) {
                composable(Routes.LOGIN) {
                    LoginScreen(onLogin = {
                        navController.navigate(Routes.LOGIN_FORM)
                    })
                }

                composable(Routes.LOGIN_FORM) {
                    LoginFormScreen(
                        onPasswordReset = {
                            navController.navigate(Routes.LOGIN) // TODO: Navigate to password reset
                        },
                        onLogin = {
                            navController.navigate(Routes.LOGIN) // TODO: Navigate to home
                        }
                    )
                }
            }
        }
    }
}