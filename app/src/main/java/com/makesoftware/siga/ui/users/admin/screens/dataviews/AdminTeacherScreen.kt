package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen


@Composable
fun AdminTeacherScreen(modifier: Modifier = Modifier) {
    AdminDataViewScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Nível", 1.5F, TextAlign.Center),
            DataGridColumnProperties("Idade", 0.7F, TextAlign.Center),
        ),
        items = listOf(
            DataGridRowContent(
                listOf(
                    "Flávio Viotti", "Mestre", "35"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Rodrigo Amorim", "Doutor", "45"
                )
            ),
            DataGridRowContent(
                listOf(
                    "Humberto", "Faixa preta", "25"
                )
            ),
        ),
        onAddEntity = { /* TODO */ },
        modifier = modifier
    )
}