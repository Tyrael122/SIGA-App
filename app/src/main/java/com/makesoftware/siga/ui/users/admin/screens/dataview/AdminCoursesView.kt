package com.makesoftware.siga.ui.users.admin.screens.dataview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen

@Composable
fun AdminCourseScreen(onAddCourse: () -> Unit, modifier: Modifier = Modifier) {
    AdminDataViewScreen(
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
        onAddEntity = onAddCourse,
        modifier = modifier
    )
}