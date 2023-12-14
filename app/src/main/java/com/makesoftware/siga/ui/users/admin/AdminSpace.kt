package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.makesoftware.siga.ui.commons.AppSpace
import com.makesoftware.siga.ui.commons.NavigationItem
import com.makesoftware.siga.ui.commons.createNavigationItem

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

    val items = listOf(
        createNavigationItem(label = "Home",
            route = AdminRoutes.HOME,
            imageVector = Icons.Outlined.Home,
            onClick = { navController.navigate(AdminRoutes.HOME) }),
        createNavigationItem(label = "Cursos",
            route = AdminRoutes.CURSOS,
            imageVector = Icons.Outlined.School,
            onClick = { navController.navigate(AdminRoutes.CURSOS) }),
        createNavigationItem(label = "Matérias",
            route = AdminRoutes.MATERIAS,
            imageVector = Icons.Outlined.Home,
            onClick = { navController.navigate(AdminRoutes.MATERIAS) }),
    )

    AppSpace(items = items, currentRoute = currentRoute, onLogout = onLogout, topAppBarTitle = {
        AppBarTitle(
            items = items,
            currentRoute = currentRoute,
        )
    }) {
        AdminNavGraph(navController, it)
    }
}

@Composable
fun AppBarTitle(items: List<NavigationItem>, currentRoute: String, modifier: Modifier = Modifier) {
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }

    Text(
        text = items[selectedIndex].label,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 10.dp)
    )
}
