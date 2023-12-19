package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminTeacherForm(
    teacher: Teacher,
    updateTeacherData: (Teacher) -> Unit,
    saveTeacherUpdate: () -> Unit,
    saveTeacher: () -> Unit,
    isUpdate: Boolean
) {
    AdminFormScreen(
        saveEntity = saveTeacher,
        updateEntity = saveTeacherUpdate,
        isUpdate = isUpdate,
    ) {
        FormTextField(
            value = teacher.name,
            onValueChange = { updateTeacherData(teacher.copy(name = it)) },
            placeholderText = "Nome"
        )


    }
}
