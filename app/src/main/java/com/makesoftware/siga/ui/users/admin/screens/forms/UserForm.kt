package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import com.makesoftware.siga.data.User
import com.makesoftware.siga.ui.commons.components.FormTextField

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