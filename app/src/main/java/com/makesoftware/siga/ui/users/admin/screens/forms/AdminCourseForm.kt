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

@Composable
fun AdminCourseForm(
    course: Course,
    saveCourse: () -> Unit,
    saveCourseUpdate: () -> Unit,
    onSelectSubjectsRequest: () -> Unit,
    updateCourseData: (Course) -> Unit,
    isUpdate: Boolean,
) {
    AdminFormScreen(
        saveEntity = saveCourse,
        updateEntity = saveCourseUpdate,
        isUpdate = isUpdate,
    ) {
        FormTextField(
            value = course.name,
            onValueChange = { updateCourseData(course.copy(name = it)) },
            placeholderText = "Nome"
        )

        FormTextField(
            value = course.description,
            onValueChange = { updateCourseData(course.copy(description = it)) },
            placeholderText = "Descrição",
            minLines = 3,
        )

        FormNumberTextField(
            value = course.semestersAmount, onValueChange = {
                updateCourseData(course.copy(semestersAmount = it))
            }, fieldDescription = "Quantidade de semestres"
        )

        FormNumberTextField(
            value = course.semestersToFinish, onValueChange = {
                updateCourseData(course.copy(semestersToFinish = it))
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
