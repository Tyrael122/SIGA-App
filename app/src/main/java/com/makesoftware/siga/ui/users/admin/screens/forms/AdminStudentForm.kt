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

        var selectedOptionText by remember { mutableStateOf(student.enrolledCourse.name) }
        FormDropdownMenu(
            options = courses.map { it.name },
            selectedOptionText = selectedOptionText,
            // TODO: This could be refactored to inside the FormDropdownMenu.
            //  You could have the onSelectionChange function return a object of type T, based on what you passed in the options param.
            //  But wait for another code repetition to do it.
            onSelectionChanged = {
                val selectedCourse = courses.find { course -> course.name == it }
                selectedCourse?.let { course ->
                    updateStudentData(student.copy(enrolledCourse = course))
                }

                selectedOptionText = it
            },
            placeholder = { Text("Selecione um curso") },
        )

        FormSelectableDataGrid(
            infoText = "Disciplinas",
            onSelectRequest = onSelectSubjectsRequest,
            numberOfSelectedItems = student.enrolledSubjects.size,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}