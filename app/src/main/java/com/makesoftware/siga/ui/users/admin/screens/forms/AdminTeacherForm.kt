package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
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

        FormTextField(
            value = teacher.urlCurriculoLattes, onValueChange = {
                updateTeacherData(teacher.copy(urlCurriculoLattes = it))
            }, placeholderText = "URL do Currículo Lattes", minLines = 2
        )
    }
}


@Composable
fun UserForm(user: User, updateUser: (User) -> Unit) {
    FormTextField(
        value = user.firstName, onValueChange = {
            updateUser(user.copy(firstName = it))
        }, placeholderText = "Nome"
    )

    FormTextField(
        value = user.cpf, onValueChange = {
            updateUser(user.copy(cpf = it))
        }, placeholderText = "CPF"
    )

    FormTextField(
        value = user.email, onValueChange = {
            updateUser(user.copy(email = it))
        }, placeholderText = "Email"
    )

    FormTextField(
        value = user.password, onValueChange = {
            updateUser(user.copy(password = it))
        }, placeholderText = "Senha"
    )
}