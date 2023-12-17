package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.data.datasources.Course
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen
import com.makesoftware.siga.ui.users.admin.viewmodels.DataGridState

@Composable
fun AdminCourseScreen(
    modifier: Modifier = Modifier,
    onAddCourse: () -> Unit,
    fetchCourses: () -> Unit,
    dataGridState: DataGridState
) {
    AdminDataViewScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
        ),
        dataGridState = dataGridState,
        onAddEntity = onAddCourse,
        fetchEntities = fetchCourses,
        modifier = modifier
    )
}