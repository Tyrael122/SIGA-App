package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataGridScreen


@Composable
fun AdminSubjectScreen(modifier: Modifier = Modifier) {
    AdminDataGridScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
            DataGridColumnProperties("Semestre", 1F, TextAlign.Center)
        ),
        items = listOf(
            DataGridRowContent(
                listOf(
                    "Laboratório de Engenharia de Software", "ADS", "1"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Economia", "ADS", "2"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Sistemas Operacionais", "ADS", "3"
                )
            ),
        ),
        onAddEntity = { /* TODO */ },
        modifier = modifier
    )
}