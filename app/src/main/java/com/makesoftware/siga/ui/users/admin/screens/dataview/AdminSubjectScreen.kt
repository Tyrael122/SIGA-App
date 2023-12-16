package com.makesoftware.siga.ui.users.admin.screens.dataview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen


@Composable
fun AdminSubjectScreen(modifier: Modifier = Modifier) {
    AdminDataViewScreen(
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