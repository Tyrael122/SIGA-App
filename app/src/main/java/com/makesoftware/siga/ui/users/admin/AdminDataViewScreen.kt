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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.data.DataGridView
import com.makesoftware.siga.network.FetchResult
import com.makesoftware.siga.ui.commons.components.DataGrid
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.users.admin.viewmodels.BasicCrudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : DataGridView> AdminDataViewScreen(
    modifier: Modifier = Modifier,
    columns: List<DataGridColumnProperties>,
    onItemClick: (T) -> Unit = {},
    onAddEntityRequest: () -> Unit,
    fetchItems: () -> Unit,
    fetchResult: FetchResult<T>
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEntityRequest,
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

@Composable
fun <T : DataGridView> AdminDataViewScreenWrapper(
    modifier: Modifier = Modifier,
    navigateToFormScreen: () -> Unit,
    columns: List<DataGridColumnProperties>,
    viewModel: BasicCrudViewModel<T>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    AdminDataViewScreen(modifier = modifier,
        columns = columns,
        onItemClick = { viewModel.selectEntity(it) },
        onAddEntityRequest = {
            viewModel.clearForm()
            navigateToFormScreen()
        },
        fetchItems = { viewModel.fetchEntity(context) },
        fetchResult = uiState.fetchResult
    )
}