package com.makesoftware.siga.ui.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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