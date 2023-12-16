package com.makesoftware.siga.ui.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.theme.DataGridTypograhpy
import com.makesoftware.siga.ui.theme.secondary_color

@Composable
fun DataGrid(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
    backgroundColor: Color = secondary_color,
    items: List<DataGridRowContent>,
    columns: List<DataGridColumnProperties>
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            SearchBar(Modifier.padding(horizontal = 12.dp))

            Spacer(Modifier.height(20.dp))

            LazyItemGrid(
                items = items, columns = columns, onItemClick = onItemClick,
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
private fun LazyItemGrid(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    columns: List<DataGridColumnProperties>,
    items: List<DataGridRowContent>
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        DataGridHeader(
            columns = columns, modifier = Modifier
                .padding(horizontal = 12.dp)
                .heightIn(min = 45.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

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

// TODO: Maybe add a padding property in the future.
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
            placeholderText = "Search...",
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