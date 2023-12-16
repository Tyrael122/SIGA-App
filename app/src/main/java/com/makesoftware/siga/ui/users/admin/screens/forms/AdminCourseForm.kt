package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.FormNumberTextField
import com.makesoftware.siga.ui.commons.components.FormSelectableDataGrid
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminCourseForm(
    commitButtonText: String, onCommitRequest: () -> Unit, onSelectSubjectsRequest: () -> Unit
) {
    AdminFormScreen(
        commitButtonText = commitButtonText,
        onCommitRequest = onCommitRequest,
    ) {
        var name by rememberSaveable { mutableStateOf("") }
        FormTextField(
            value = name, onValueChange = { name = it }, placeholderText = "Nome"
        )

        var descricao by rememberSaveable { mutableStateOf("") }
        FormTextField(
            value = descricao,
            onValueChange = { descricao = it },
            placeholderText = "Descrição",
            minLines = 3
        )

        var quantidadeDeSemestres by rememberSaveable { mutableIntStateOf(0) }
        FormNumberTextField(
            value = quantidadeDeSemestres,
            onValueChange = { quantidadeDeSemestres = it ?: 0 },
            fieldDescription = "Quantidade de semestres",
        )

        var semestresParaFinalizacao by rememberSaveable { mutableIntStateOf(0) }
        FormNumberTextField(
            value = semestresParaFinalizacao,
            onValueChange = { semestresParaFinalizacao = it ?: 0 },
            fieldDescription = "Semestres para finalização",
        )

        FormSelectableDataGrid(
            infoText = "Disciplinas",
            onSelectRequest = onSelectSubjectsRequest,
            numberOfSelectedItems = 10,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}
