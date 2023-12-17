package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.data.datasources.Course
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen

@Composable
fun AdminCourseScreen(
    modifier: Modifier = Modifier,
    onAddCourse: () -> Unit,
    courses: List<Course>,
    fetchCourses: () -> Unit,
    isLoading: Boolean
) {
    val items = courses.map {
        DataGridRowContent(
            listOf(it.name, it.acronym)
        )
    }

    AdminDataViewScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
        ),
        items = items,
        onAddEntity = onAddCourse,
        fetchEntities = fetchCourses,
        isLoading = isLoading,
        modifier = modifier
    )
}