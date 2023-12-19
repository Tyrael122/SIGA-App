package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.DefaultElevatedButton
import com.makesoftware.siga.ui.users.admin.viewmodels.FormState

@Composable
fun AdminFormScreen(
    modifier: Modifier = Modifier,
    saveButtonText: String = "Salvar",
    readonlyButtonText: String = "Editar",
    updateButtonText: String = "Salvar edições",
    onCommitRequest: (FormState) -> Unit,
    formState: FormState = FormState.Save, // TODO: Remove default value
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

        DefaultElevatedButton(
            onClick = { onCommitRequest(formState) },
            text = buttonText,
            modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}

val LocalIsReadOnly = compositionLocalOf(defaultFactory = { false })