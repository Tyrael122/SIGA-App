package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class NavigationItem(
    val label: String, val icon: @Composable () -> Unit = {}, val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminSpace(onLogout: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedIndex by remember { mutableIntStateOf(0) }

    val items = listOf(NavigationItem(label = "Home", onClick = { /* TODO */ }, icon = {
        Icon(
            Icons.Outlined.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )
    }), NavigationItem(label = "Cursos", onClick = { /* TODO */ }, icon = {
        Icon(
            Icons.Outlined.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )
    }), NavigationItem(label = "Matérias", onClick = { /* TODO */ }, icon = {
        Icon(
            Icons.Outlined.Home,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )
    })
    )

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            drawerShape = RectangleShape,
            modifier = Modifier.widthIn(max = 300.dp),
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {

                Column {
                    UserInfo(modifier = Modifier.padding(top = 20.dp, bottom = 45.dp))

                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(label = {
                            Text(
                                text = item.label,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        },
                            icon = item.icon,
                            selected = index == selectedIndex,
                            onClick = {
                                selectedIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                                item.onClick()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)
                        )
                    }
                }

                LogoutButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 35.dp)
                )
            }
        }
    }) {
        UserScreenContent(items[selectedIndex].label, drawerState = drawerState)
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ), modifier = modifier
    ) {
        Icon(
            Icons.Outlined.ExitToApp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = "Logout",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun UserInfo(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            Icons.Filled.AccountCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "Kauan Martins",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp)
        )

        Text(
            text = "Coordenador",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenContent(titleText: String, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            DefaultTopAppBar(title = {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }, onNavigationIconClick = {
                scope.launch {
                    drawerState.open()
                }
            })
        },
    ) { innerPadding ->
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Column() {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    onNavigationIconClick: () -> Unit, title: @Composable () -> Unit, modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = MaterialTheme.colorScheme.primary,
    ), title = {
        title()
    }, navigationIcon = {
        DefaultNavigationIcon(modifier = Modifier.padding(start = 8.dp), onNavigationIconClick)
    }, modifier = modifier
    )
}

@Composable
fun DefaultNavigationIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() }, modifier = modifier
    ) {
        Icon(
            Icons.Filled.Menu,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(50.dp)
        )
    }
}
