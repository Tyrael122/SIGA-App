package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.makesoftware.siga.R
import com.makesoftware.siga.ui.commons.AppSpace
import com.makesoftware.siga.ui.commons.NavigationItem
import com.makesoftware.siga.ui.commons.createNavigationItem

@Composable
fun AdminSpace(onLogout: () -> Unit) {

    val navController = rememberNavController()

    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val items = listOf(
        createNavigationItem(
            label = "Home",
            route = AdminRoutes.HOME,
            imageVector = Icons.Outlined.Home,
            onClick = { /* TODO */ }),
        createNavigationItem(
            label = "Cursos",
            route = AdminRoutes.CURSOS,
            imageVector = Icons.Outlined.Home,
            onClick = { /* TODO */ }),
        createNavigationItem(
            label = "Matérias",
            route = AdminRoutes.MATERIAS,
            imageVector = Icons.Outlined.Home,
            onClick = { /* TODO */ })
    )

    AppSpace(items = items, currentRoute = currentRoute, onLogout = onLogout, topAppBarTitle = {
        AppBarTitle(
            items = items,
            currentRoute = currentRoute,
        )
    }) {
        AdminNavGraph(navController)
    }
}

@Composable
fun AppBarTitle(items: List<NavigationItem>, currentRoute: String?, modifier: Modifier = Modifier) {
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }

    Text(
        text = items[selectedIndex].label,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 10.dp)
    )
}
