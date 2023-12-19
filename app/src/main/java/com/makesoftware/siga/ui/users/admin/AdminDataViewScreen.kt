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
import com.makesoftware.siga.data.DataGridView
import com.makesoftware.siga.ui.commons.components.DataGrid
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.viewmodels.FetchResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : DataGridView> AdminDataViewScreen(
    modifier: Modifier = Modifier,
    columns: List<DataGridColumnProperties>,
    items: List<DataGridRowContent> = listOf(), // TODO: Remove this
    onItemClick: (T) -> Unit = {},
    onAddEntity: () -> Unit,
    fetchItems: () -> Unit = {},
    isLoading: Boolean = false, // TODO: Remove this
    fetchResult: FetchResult<T> = FetchResult.Loading(), // TODO: Remove the default.
) {
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
        DataGrid(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .padding(top = 30.dp, bottom = 15.dp),
            onItemClick = onItemClick,
            fetchResult = fetchResult,
            columns = columns,
            fetchData = fetchItems,
        )
    }
}