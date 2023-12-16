package com.makesoftware.siga.ui.users.admin.screens.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.makesoftware.siga.ui.commons.DefaultElevatedButton

@Composable
fun AdminCourseForm(onSaveCourse: () -> Unit) {
    Column () {
        DefaultElevatedButton(onClick = onSaveCourse, text = "Salvar")
    }
}
