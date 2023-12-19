package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.ui.commons.components.FormNumberTextField
import com.makesoftware.siga.ui.commons.components.FormSelectableDataGrid
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen
import com.makesoftware.siga.ui.users.admin.viewmodels.FormState

@Composable
fun AdminCourseForm(
    course: Course,
    onCommitRequest: (FormState) -> Unit,
    onSelectSubjectsRequest: () -> Unit,
    updateCourse: (Course) -> Unit,
    formState: FormState,
) {
    AdminFormScreen(
        onCommitRequest = onCommitRequest,
        formState = formState,
    ) {
        FormTextField(
            value = course.name,
            onValueChange = { updateCourse(course.copy(name = it)) },
            placeholderText = "Nome"
        )

        FormTextField(
            value = course.acronym,
            onValueChange = { updateCourse(course.copy(acronym = it)) },
            placeholderText = "Sigla",
        )

        FormTextField(
            value = course.description,
            onValueChange = { updateCourse(course.copy(description = it)) },
            placeholderText = "Descrição",
            minLines = 3,
        )

        FormNumberTextField(
            value = course.semestersAmount, onValueChange = {
                updateCourse(course.copy(semestersAmount = it ?: 0))
            }, fieldDescription = "Quantidade de semestres"
        )

        FormNumberTextField(
            value = course.semestersToFinish, onValueChange = {
                updateCourse(course.copy(semestersToFinish = it ?: 0))
            }, fieldDescription = "Semestres para finalização"
        )

        FormSelectableDataGrid(
            infoText = "Disciplinas",
            onSelectRequest = onSelectSubjectsRequest,
            numberOfSelectedItems = 10,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}
