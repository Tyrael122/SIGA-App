package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.data.DataGridView
import com.makesoftware.siga.network.ErrorType
import com.makesoftware.siga.network.FetchResult
import com.makesoftware.siga.ui.theme.AlternativeColorScheme
import com.makesoftware.siga.ui.theme.DataGridTypograhpy

@Composable
fun <T : DataGridView> DataGrid(
    modifier: Modifier = Modifier,
    onItemClick: (T) -> Unit = {},
    backgroundColor: Color = AlternativeColorScheme.secondary_color,
    columns: List<DataGridColumnProperties>,
    fetchData: () -> Unit,
    fetchResult: FetchResult<T>,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(top = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            SearchBar()

            DataGridHeader(
                columns = columns, modifier = Modifier.heightIn(min = 45.dp)
            )
        }

        ItemGrid(
            columns = columns,
            onItemClick = onItemClick,
            fetchData = fetchData,
            fetchResult = fetchResult,
        )
    }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        FilterButton()

        Spacer(Modifier.width(16.dp))

        SearchTextField(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DataGridHeader(modifier: Modifier = Modifier, columns: List<DataGridColumnProperties>) {
    DataGridRow(
        columnContentMap = columns.associateWith { it.name },
        style = DataGridTypograhpy.bodyMedium,
        textColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun <T : DataGridView> ItemGrid(
    modifier: Modifier = Modifier,
    onItemClick: (T) -> Unit,
    fetchData: () -> Unit,
    columns: List<DataGridColumnProperties>,
    fetchResult: FetchResult<T>,
) {

    val isLoading = fetchResult is FetchResult.Loading

    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = {
        fetchData()
    })

    Box(
        modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        when (fetchResult) {
            is FetchResult.Error -> {
                DataGridErrorIndicator(
                    error = fetchResult,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                )
            }

            is FetchResult.Success -> {
                LazyItemGrid(
                    items = fetchResult.items.map { it.toDataGridView() },
                    columns = columns,
                    onItemClick = {
                        onItemClick(fetchResult.items[it])
                    },
                )
            }

            else -> {} // Do nothing here. The pull refresh indicator should be shown.
        }

        PullRefreshIndicator(isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun <T> DataGridErrorIndicator(modifier: Modifier = Modifier, error: FetchResult.Error<T>) {
    when (error.errorType) {
        ErrorType.NO_NETWORK -> NoInternetIndicator(modifier = modifier)
        ErrorType.UNKNOWN -> IconIndicator(
            modifier = modifier,
            text = error.message,
            icon = Icons.Outlined.ErrorOutline,
        )
    }
}

@Composable
private fun LazyItemGrid(
    modifier: Modifier = Modifier,
    items: List<DataGridRowContent>,
    columns: List<DataGridColumnProperties>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {

        if (items.isEmpty()) {
            item {
                NoContentIndicator(
                    text = "Sem registros.\nPuxe para baixo para atualizar.",
                    modifier = Modifier.fillParentMaxSize()
                )
            }
        }

        items(items.size) { index ->
            val backgroundColor =
                if (index % 2 == 0) MaterialTheme.colorScheme.surface else Color.Transparent

            val dataGridRowContent = items[index]
            val columnContentMap = generateColumnContentMap(columns, dataGridRowContent)

            DataGridRow(columnContentMap = columnContentMap,
                modifier = Modifier
                    .heightIn(min = 60.dp)
                    .background(backgroundColor)
                    .clickable { onItemClick(index) }
                    .padding(horizontal = 12.dp))
        }
    }
}

@Composable
private fun DataGridRow(
    columnContentMap: Map<DataGridColumnProperties, String>,
    modifier: Modifier = Modifier,
    style: TextStyle = DataGridTypograhpy.bodyLarge,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        columnContentMap.forEach {
            val text = it.value
            val dataGridColumn = it.key

            Text(
                text = text,
                textAlign = dataGridColumn.textAlign,
                style = style,
                color = textColor,
                modifier = Modifier
                    .weight(dataGridColumn.weight)
                    .padding(vertical = 10.dp)
            )
        }
    }
}

data class DataGridColumnProperties(
    val name: String, val weight: Float, val textAlign: TextAlign = TextAlign.Start
)

data class DataGridRowContent(val texts: List<String>)


@Composable
fun FilterButton(modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = { /*TODO*/ },
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight()
    ) {
        Icon(Icons.Filled.FilterAlt, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxHeight()
    ) {

        var text by remember { mutableStateOf("") }

        DefaultOutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            textStyle = DataGridTypograhpy.bodyLarge.copy(fontWeight = FontWeight.Normal),
            placeholderText = "Pesquisar...",
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            contentPadding = PaddingValues(horizontal = 10.dp),
            unfocusedBorderThickness = 0.dp,
            focusedBorderThickness = 0.dp,
            modifier = Modifier
                .weight(1f)
                .shadow(1.dp, RoundedCornerShape(10.dp))
        )

    }
}

private fun generateColumnContentMap(
    columns: List<DataGridColumnProperties>, dataGridRowContent: DataGridRowContent
): Map<DataGridColumnProperties, String> {

    var columnContentMap = mapOf<DataGridColumnProperties, String>()
    for (i in columns.indices) {

        var text = ""
        if (i < dataGridRowContent.texts.size) {
            text = dataGridRowContent.texts[i]
        }

        columnContentMap = columnContentMap.plus(Pair(columns[i], text))
    }

    return columnContentMap
}