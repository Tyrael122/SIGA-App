package com.makesoftware.siga.ui.commons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSpace(
    items: List<NavigationItem>,
    currentRoute: String?,
    topAppBarTitle: @Composable () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(modifier = modifier, drawerState = drawerState, drawerContent = {
        DrawerContent(items = items, currentRoute = currentRoute, onLogout = onLogout, closeDrawer = {
            scope.launch {
                drawerState.close()
            }
        })
    }) {
        Scaffold(
            topBar = {
                DefaultTopAppBar(title = topAppBarTitle, onNavigationIconClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
            },
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}
