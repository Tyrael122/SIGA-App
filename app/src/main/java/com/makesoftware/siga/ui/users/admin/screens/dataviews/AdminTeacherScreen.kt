package com.makesoftware.siga.ui.users.admin.screens.dataviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.ui.commons.components.DataGridColumnProperties
import com.makesoftware.siga.ui.users.admin.AdminDataViewScreen
import com.makesoftware.siga.network.FetchResult


@Composable
fun AdminTeacherScreen(
    modifier: Modifier = Modifier,
    onAddTeachers: () -> Unit,
    fetchTeachers: () -> Unit,
    fetchResult: FetchResult<Teacher>,
    onSelectedTeacher: (Teacher) -> Unit
) {
    AdminDataViewScreen(
        columns = listOf(
            DataGridColumnProperties("Nome", 2F, TextAlign.Start),
            DataGridColumnProperties("Nível", 1.5F, TextAlign.Center),
            DataGridColumnProperties("Idade", 0.7F, TextAlign.Center),
        ),
        fetchItems = fetchTeachers,
        fetchResult = fetchResult,
        onAddEntityRequest = onAddTeachers,
        onItemClick = onSelectedTeacher,
        modifier = modifier,
    )
}