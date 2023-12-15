package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataGridScreen

@Composable
fun AdminCourseScreen(modifier: Modifier = Modifier) {
    AdminDataGridScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
        ),
        items = listOf(
            DataGridRowContent(
                listOf(
                    "Análise e Desenvolvimento de Sistemas", "ADS"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Ciência da Computação", "CCI"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Direito", "Law"
                )
            ),
        ),
        onAddEntity = { /* TODO */ },
        modifier = modifier
    )
}