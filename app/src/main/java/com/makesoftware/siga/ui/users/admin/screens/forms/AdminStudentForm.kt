package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.FormDropdownMenu
import com.makesoftware.siga.ui.commons.components.FormNumberTextField
import com.makesoftware.siga.ui.commons.components.FormSelectableDataGrid
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminStudentForm(
    onSelectSubjectsRequest: () -> Unit
) {
    AdminFormScreen {
        var name by rememberSaveable { mutableStateOf("") }
        FormTextField(
            value = name, onValueChange = { name = it }, placeholderText = "Nome"
        )

        var semestreBase by rememberSaveable { mutableIntStateOf(0) }
        FormNumberTextField(
            value = semestreBase,
            onValueChange = { semestreBase = it ?: 0 },
            fieldDescription = "Semestre base",
        )

        val cursos = listOf(
            "Ciência da Computação", "Engenharia de Software", "Direito", "Biologia", "Medicina"
        )
        var selectedOptionText by remember { mutableStateOf("") }

        FormDropdownMenu(
            options = cursos,
            selectedOptionText = selectedOptionText,
            onSelectionChanged = { selectedOptionText = it },
            placeholder = { Text("Selecione um curso") },
        )

        FormSelectableDataGrid(
            infoText = "Disciplinas",
            onSelectRequest = onSelectSubjectsRequest,
            numberOfSelectedItems = 10,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}