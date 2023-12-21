package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backpack
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.makesoftware.siga.ui.commons.AppSpace
import com.makesoftware.siga.ui.commons.NavigationItem
import com.makesoftware.siga.ui.commons.createNavigationItem
import com.makesoftware.siga.ui.theme.alternativeTypography

@Composable
fun AdminSpace(onLogout: () -> Unit) {

    val navController = rememberNavController()

    var currentRoute by remember {
        mutableStateOf(
            navController.currentBackStackEntry?.destination?.route ?: AdminRoutes.HOME
        )
    }

    navController.addOnDestinationChangedListener(listener = { controller, destination, arguments ->
        currentRoute = destination.route ?: AdminRoutes.HOME
    })

    val navigationItems = createNavigationItems(navController)

    AppSpace(items = navigationItems,
        currentRoute = currentRoute,
        onLogout = onLogout,
        topAppBarTitle = {
            AppBarTitle(
                items = navigationItems,
                currentRoute = currentRoute,
            )
        }) {

        AdminNavGraph(
            navController = navController, paddingValues = it
        )
    }
}

fun createNavigationItems(navController: NavHostController): List<NavigationItem> {
    return listOf(
        createNavigationItem(label = "Home",
            route = AdminRoutes.HOME,
            imageVector = Icons.Outlined.Home,
            onClick = { navController.navigate(it) }),

        createNavigationItem(label = "Cursos",
            route = AdminRoutes.COURSES,
            imageVector = Icons.Outlined.School,
            onClick = { navController.navigate(it) }),

        createNavigationItem(label = "Matérias",
            route = AdminRoutes.SUBJECTS,
            imageVector = Icons.Outlined.Book,
            onClick = { navController.navigate(it) }),

        createNavigationItem(label = "Professores",
            route = AdminRoutes.TEACHERS,
            imageVector = Icons.Outlined.Person,
            onClick = { navController.navigate(it) }),

        createNavigationItem(label = "Alunos",
            route = AdminRoutes.STUDENTS,
            imageVector = Icons.Outlined.Backpack,
            onClick = { navController.navigate(it) }),
    )
}

@Composable
fun AppBarTitle(items: List<NavigationItem>, currentRoute: String, modifier: Modifier = Modifier) {
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }
    if (selectedIndex != -1) {
        AppBarTitleText(text = items[selectedIndex].label, modifier = modifier)
        return
    }

    val text = when (currentRoute) {
        AdminRoutes.COURSE_FORM -> "Cadastro\n de Curso"
        AdminRoutes.STUDENT_FORM -> "Cadastro\n de Aluno"
        AdminRoutes.TEACHER_FORM -> "Cadastro\n de Professor"
        else -> ""
    }

    AppBarTitleText(
        text = text,
        style = alternativeTypography.bodyLarge.copy(textAlign = TextAlign.Center),
        modifier = modifier
    )
}

@Composable
fun AppBarTitleText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
) {
    Text(
        text = text,
        style = style,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 10.dp)
    )
}
