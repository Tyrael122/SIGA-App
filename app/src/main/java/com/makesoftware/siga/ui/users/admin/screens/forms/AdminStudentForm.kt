package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.runtime.Composable
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.ui.commons.components.FormTextField
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminStudentForm(
    student: Student,
    updateStudentData: (Student) -> Unit,
    saveStudent: () -> Unit,
    updateStudent: () -> Unit,
    isUpdate: Boolean
) {
    AdminFormScreen(
        saveEntity = saveStudent,
        updateEntity = updateStudent,
        isUpdate = isUpdate,
    ) {

        FormTextField(
            value = student.name,
            onValueChange = { updateStudentData(student.copy(name = it)) },
            placeholderText = "Nome"
        )

//        var semestreBase by rememberSaveable { mutableIntStateOf(0) }
//        FormNumberTextField(
//            value = semestreBase,
//            onValueChange = { semestreBase = it ?: 0 },
//            fieldDescription = "Semestre base",
//        )
//
//        val cursos = listOf(
//            "Ciência da Computação", "Engenharia de Software", "Direito", "Biologia", "Medicina"
//        )
//        var selectedOptionText by remember { mutableStateOf("") }
//
//        FormDropdownMenu(
//            options = cursos,
//            selectedOptionText = selectedOptionText,
//            onSelectionChanged = { selectedOptionText = it },
//            placeholder = { Text("Selecione um curso") },
//        )
//
//        FormSelectableDataGrid(
//            infoText = "Disciplinas",
//            onSelectRequest = onSelectSubjectsRequest,
//            numberOfSelectedItems = 10,
//            modifier = Modifier.padding(top = 30.dp)
//        )
    }
}