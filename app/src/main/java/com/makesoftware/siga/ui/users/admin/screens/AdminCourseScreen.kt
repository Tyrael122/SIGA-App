package com.makesoftware.siga.ui.users.admin.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.DataGrid
import com.makesoftware.siga.ui.commons.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.DataGridRowContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCourseScreen(modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }, modifier = modifier
    ) {
        var items = listOf(
            DataGridRowContent(
                listOf(
                    "Laboratório de Engenharia de Software", "ADS", // "2023"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Economia", "ADS", // "2021"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Sistemas Operacionais", "ADS", // "2021"
                )
            ),
        )

        for (i in 0..10) {
            items = items.plus(items)
        }

        // TODO: Use map to create DataGridItem
        DataGrid(
            items = items,
            columns = listOf(
                DataGridColumnProperties("Nome", 2F, TextAlign.Start),
                DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
                DataGridColumnProperties("Ano", 1F, TextAlign.Center)
            ),
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
                .padding(top = 30.dp, bottom = 15.dp)
        )
    }
}