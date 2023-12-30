package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import com.makesoftware.siga.ui.commons.components.FormDropdownMenu
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
        val user = teacher.user
        val updateUser = { incomingUser: User ->
            updateTeacherData(teacher.copy(user = incomingUser))
        }

        UserForm(user, updateUser)

        val graduationLevels = listOf("Graduação", "Mestrado", "Doutorado", "Especialização")
        var selectedOptionText by remember { mutableStateOf(teacher.graduationLevel) }
        FormDropdownMenu(options = graduationLevels,
            selectedOptionText = teacher.graduationLevel,
            onSelectionChanged = {
                // TODO: Refactor this. It's a code repetition from AdminStudentForm.kt
                val selectedGraduationLevel =
                    graduationLevels.find { graduationLevel -> graduationLevel == it }
                selectedGraduationLevel?.let { graduationLevel ->
                    updateTeacherData(teacher.copy(graduationLevel = graduationLevel))
                }

                selectedOptionText = it
            })

        FormTextField(
            value = teacher.urlCurriculoLattes, onValueChange = {
                updateTeacherData(teacher.copy(urlCurriculoLattes = it))
            }, placeholderText = "URL do Currículo Lattes", minLines = 2
        )
    }
}