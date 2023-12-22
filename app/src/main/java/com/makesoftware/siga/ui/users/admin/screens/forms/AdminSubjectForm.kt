package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import com.makesoftware.siga.data.Subject
import com.makesoftware.siga.ui.commons.components.FormNumberTextField
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminSubjectForm(
    subject: Subject,
    updateSubjectData: (Subject) -> Unit,
    saveSubject: () -> Unit,
    updateSubject: () -> Unit,
    isUpdate: Boolean
) {
    AdminFormScreen(
        saveEntity = saveSubject,
        updateEntity = updateSubject,
        isUpdate = isUpdate,
    ) {

        FormTextField(
            value = subject.name,
            onValueChange = { updateSubjectData(subject.copy(name = it)) },
            placeholderText = "Nome"
        )

        FormTextField(
            value = subject.description,
            onValueChange = { updateSubjectData(subject.copy(description = it)) },
            placeholderText = "Descrição"
        )

        FormNumberTextField(
            value = subject.workload,
            onValueChange = { updateSubjectData(subject.copy(workload = it)) },
            fieldDescription = "Carga horária",
            maxCharCount = 3
        )

        FormNumberTextField(
            value = subject.defaultSemester,
            onValueChange = { updateSubjectData(subject.copy(defaultSemester = it)) },
            fieldDescription = "Semestre base"
        )
    }
}