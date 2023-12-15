package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataGridScreen


@Composable
fun AdminStudentScreen(modifier: Modifier = Modifier) {
    AdminDataGridScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Curso", 1F, TextAlign.Left),
            DataGridColumnProperties("Semestre", 1F, TextAlign.Center)
        ),
        items = listOf(
            DataGridRowContent(
                listOf(
                    "Kauan Borges", "ADS", "1"
                )
            ),
            DataGridRowContent(
                listOf(
                    "João Vitor", "COMEX", "2"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Ana Clara", "SEGINFO", "3"
                )
            ),
        ),
        onAddEntity = { /* TODO */ },
        modifier = modifier
    )
}