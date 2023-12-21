package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.DefaultElevatedButton

@Composable
fun AdminFormScreen(
    modifier: Modifier = Modifier,
    saveButtonText: String = "Salvar",
    readonlyButtonText: String = "Editar",
    updateButtonText: String = "Salvar edições",
    saveEntity: () -> Unit,
    updateEntity: () -> Unit,
    isUpdate: Boolean,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .padding(top = 40.dp)
    ) {
        val initialFormState = if (isUpdate) FormState.Readonly else FormState.Save
        var formState: FormState by remember { mutableStateOf(initialFormState) }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            val isTextReadOnly = formState == FormState.Readonly

            CompositionLocalProvider(LocalIsReadOnly provides isTextReadOnly) {
                content()
            }
        }

        val buttonText = when (formState) {
            FormState.Save -> saveButtonText
            FormState.Readonly -> readonlyButtonText
            FormState.Update -> updateButtonText
        }

        val changeFormState = generateNextFormState(formState)

        DefaultElevatedButton(
            onClick = {
                val result = when (formState) {
                    FormState.Save -> saveEntity()
                    FormState.Update -> updateEntity()
                    else -> {}
                }

                // TODO: If the operation fails, don't transition to the next state and show a Toast.
                formState = changeFormState

            }, text = buttonText, modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}

fun generateNextFormState(formState: FormState): FormState {
    return when (formState) {
        FormState.Readonly -> FormState.Update
        else -> FormState.Readonly
    }
}

val LocalIsReadOnly = compositionLocalOf(defaultFactory = { false })

sealed class FormState {
    object Save : FormState()
    object Readonly : FormState()
    object Update : FormState()
}