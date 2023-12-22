package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.User
import com.makesoftware.siga.ui.commons.components.FormDropdownMenu
import com.makesoftware.siga.ui.commons.components.FormNumberTextField
import com.makesoftware.siga.ui.commons.components.FormSelectableDataGrid
import com.makesoftware.siga.ui.users.admin.screens.AdminFormScreen

@Composable
fun AdminStudentForm(
    student: Student,
    updateStudentData: (Student) -> Unit,
    saveStudent: () -> Unit,
    updateStudent: () -> Unit,
    onSelectSubjectsRequest: () -> Unit,
    courses: List<Course>,
    isUpdate: Boolean
) {
    AdminFormScreen(
        saveEntity = saveStudent,
        updateEntity = updateStudent,
        isUpdate = isUpdate,
    ) {

        val user = student.user
        val updateUser = { incomingUser: User ->
            updateStudentData(student.copy(user = incomingUser))
        }

        UserForm(user, updateUser)

        FormNumberTextField(
            value = student.courseSemester,
            onValueChange = {
                updateStudentData(student.copy(courseSemester = it))
            },
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