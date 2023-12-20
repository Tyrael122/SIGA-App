package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminTeacherForm(
    teacher: Teacher?,
    updateTeacherData: (Teacher) -> Unit,
    saveTeacherUpdate: () -> Unit,
    saveTeacher: (Teacher) -> Unit,
    isUpdate: Boolean
) {
    val internalTeacher by remember {
        mutableStateOf(
            teacher ?: Teacher(
                isUpdate = isUpdate, User()
            )
        )
    }

    AdminFormScreen(
        saveEntity = { saveTeacher(internalTeacher) },
        updateEntity = saveTeacherUpdate,
        isUpdate = isUpdate,
    ) {
        val user = internalTeacher.user
        val updateUser = {
            updateTeacherData(internalTeacher.copy(user = user))
        }

        FormTextField(
            value = user.firstName, onValueChange = {
                user.firstName = it
                updateUser()
            }, placeholderText = "Nome"
        )

        FormTextField(
            value = user.cpf, onValueChange = {
                user.cpf = it
                updateUser()
            }, placeholderText = "CPF"
        )

        FormTextField(
            value = user.email, onValueChange = {
                user.email = it
                updateUser()
            }, placeholderText = "Email"
        )

        FormTextField(
            value = user.password, onValueChange = {
                user.password = it
                updateUser()
            }, placeholderText = "Senha"
        )
    }
}
