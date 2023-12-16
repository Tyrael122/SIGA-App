package com.makesoftware.siga.ui.users.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.DataGrid
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDataViewScreen(columns: List<DataGridColumnProperties>, items: List<DataGridRowContent>, onItemClick: (Int) -> Unit = {}, onAddEntity: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEntity,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }, modifier = modifier
    ) {
        var itemsMultiplied = items
        for (i in 0..10) {
            itemsMultiplied = itemsMultiplied.plus(itemsMultiplied)
        }

        DataGrid(
            items = itemsMultiplied,
            columns = columns,
            onItemClick = { /* TODO */ },
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .padding(top = 30.dp, bottom = 15.dp)
        )
    }
}