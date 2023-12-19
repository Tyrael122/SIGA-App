package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen


@Composable
fun AdminStudentScreen(modifier: Modifier = Modifier, onAddStudent: () -> Unit) {
    AdminDataViewScreen<Course>(
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
        onAddEntityRequest = onAddStudent,
        modifier = modifier
    )
}