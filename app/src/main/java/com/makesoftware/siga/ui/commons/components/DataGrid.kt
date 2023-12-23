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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.DataGridView
import com.makesoftware.siga.network.ErrorType
import com.makesoftware.siga.network.FetchResult
import com.makesoftware.siga.ui.theme.AlternativeColorScheme
import com.makesoftware.siga.ui.theme.DataGridTypograhpy
import com.makesoftware.siga.ui.theme.SIGATheme


@Composable
fun <T : DataGridView> DataGrid(
    modifier: Modifier = Modifier,
    onItemClick: (T) -> Unit = {},
    backgroundColor: Color = AlternativeColorScheme.secondary_color,
    columns: List<DataGridColumnProperties>,
    fetchData: () -> Unit,
    fetchResult: FetchResult<T>,
    isDatagridItemSelectable: Boolean = false,
    onCommitSelection: (List<T>) -> Unit = {},
    onSelectItem: (T) -> Unit = {},
) {
    Column(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(top = 12.dp)
    ) {
        var selectedItems by remember { mutableStateOf(listOf<T>()) }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            SearchBar()

            val items = if (fetchResult is FetchResult.Success) {
                fetchResult.items
            } else {
                emptyList()
            }

            DataGridHeader(
                modifier = Modifier.heightIn(min = 45.dp),
                selectAllItems = {
                    // TODO: Refactor this urgently!!
                    selectedItems = if (selectedItems.size == items.size) {
                        emptyList()
                    } else {
                        items
                    }
                },
                isAllItemsSelected = { selectedItems.size == items.size },
                isDataGridItemSelectable = isDatagridItemSelectable,
                columns = columns,
            )
        }

        // TODO: Refactor the selection logic of the datagrid, especially the parameters I'm constantly passing (isDataGridItemSelectable).
        ItemGrid(
            onItemClick = onItemClick,
            fetchData = fetchData,
            columns = columns,
            fetchResult = fetchResult,
            onSelectItem = {
                // TODO: Refactor this function.
                selectedItems = if (selectedItems.contains(it)) {
                    selectedItems.minus(it)
                } else {
                    selectedItems.plus(it)
                }

                onSelectItem(it)
            },
            isDatagridItemSelectable = isDatagridItemSelectable,
            isItemSelected = { selectedItems.contains(it) },
            modifier = Modifier.weight(1F)
        )

        if (isDatagridItemSelectable) {
            DefaultElevatedButton(
                onClick = { onCommitSelection(selectedItems) },
                text = "Salvar",
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
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
private fun DataGridHeader(
    modifier: Modifier = Modifier,
    selectAllItems: () -> Unit,
    columns: List<DataGridColumnProperties>,
    isDataGridItemSelectable: Boolean,
    isAllItemsSelected: () -> Boolean,
) {
    DataGridRow(
        columnContentMap = columns.associateWith { it.name },
        style = DataGridTypograhpy.bodyMedium,
        textColor = MaterialTheme.colorScheme.onSurface,
        isSelectable = isDataGridItemSelectable,
        onSelect = selectAllItems,
        isItemSelected = isAllItemsSelected(),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun <V : DataGridView> ItemGrid(
    modifier: Modifier = Modifier,
    onItemClick: (V) -> Unit,
    fetchData: () -> Unit,
    columns: List<DataGridColumnProperties>,
    fetchResult: FetchResult<V>,
    onSelectItem: (V) -> Unit,
    isDatagridItemSelectable: Boolean,
    isItemSelected: (V) -> Boolean,
) {

    val isLoading = fetchResult is FetchResult.Loading

    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = {
        fetchData()
    })

    Box(
        modifier.pullRefresh(pullRefreshState)
    ) {
        when (fetchResult) {
            is FetchResult.Error -> {
                DataGridErrorIndicator(
                    error = fetchResult,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState())
                )
            }

            is FetchResult.Success -> {
                val items = fetchResult.items
                LazyItemGrid(
                    items = items.map { it.toDataGridView() },
                    columns = columns,
                    onItemClick = { onItemClick(items[it]) },
                    isDataGridItemSelectable = isDatagridItemSelectable,
                    onSelectItem = { onSelectItem(items[it]) },
                    isItemSelected = { isItemSelected(items[it]) },
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
    onItemClick: (Int) -> Unit,
    onSelectItem: (Int) -> Unit,
    isItemSelected: (Int) -> Boolean,
    isDataGridItemSelectable: Boolean,
) {
    LazyColumn(
        modifier = modifier
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
                isSelectable = isDataGridItemSelectable,
                onSelect = { onSelectItem(index) },
                isItemSelected = isItemSelected(index),
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
    modifier: Modifier = Modifier,
    columnContentMap: Map<DataGridColumnProperties, String>,
    style: TextStyle = DataGridTypograhpy.bodyLarge,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    isSelectable: Boolean,
    isItemSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isSelectable) {
            Checkbox(
                checked = isItemSelected,
                onCheckedChange = { onSelect() },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(end = 10.dp)
            )
        }

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

@Preview
@Composable
fun DataGridPreview() {
    SIGATheme(useDarkTheme = false) {
        DataGrid(
            columns = listOf(
                DataGridColumnProperties("Nome", 1F, TextAlign.Start),
                DataGridColumnProperties("Descrição", 1F, TextAlign.Start),
            ), fetchData = {}, fetchResult = FetchResult.Success(
                listOf(
                    Course("Ciência da Computação", "Curso de computação", 10, 5),
                    Course("Engenharia de Software", "Curso de computação", 10, 5),
                    Course("Direito", "Curso de computação", 10, 5),
                    Course("Biologia", "Curso de computação", 10, 5),
                    Course("Medicina", "Curso de computação", 10, 5),
                    Course("Ciência da Computação", "Curso de computação", 10, 5),
                    Course("Engenharia de Software", "Curso de computação", 10, 5),
                    Course("Direito", "Curso de computação", 10, 5),
                    Course("Biologia", "Curso de computação", 10, 5),
                    Course("Medicina", "Curso de computação", 10, 5),
                    Course("Ciência da Computação", "Curso de computação", 10, 5),
                    Course("Engenharia de Software", "Curso de computação", 10, 5),
                )
            ), isDatagridItemSelectable = true
        )
    }
}