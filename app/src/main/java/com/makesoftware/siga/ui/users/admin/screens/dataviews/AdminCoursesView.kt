package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen
import com.makesoftware.siga.ui.users.admin.viewmodels.FetchResult

@Composable
fun AdminCourseScreen(
    modifier: Modifier = Modifier,
    onAddCourseRequest: () -> Unit,
    onSelectCourse: (Course) -> Unit,
    fetchCourses: () -> Unit,
    fetchResult: FetchResult<Course>
) {
    AdminDataViewScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Sigla", 1F, TextAlign.Center),
        ),
        fetchResult = fetchResult,
        onAddEntity = onAddCourseRequest,
        onItemClick = onSelectCourse,
        fetchItems = fetchCourses,
        modifier = modifier
    )
}