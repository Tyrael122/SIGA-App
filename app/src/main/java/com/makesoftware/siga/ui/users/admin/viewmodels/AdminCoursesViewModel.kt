package com.makesoftware.siga.ui.users.admin.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdminCoursesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminCourseUiState())

    val uiState: StateFlow<AdminCourseUiState> = _uiState.asStateFlow()
}


class AdminCourseUiState {

}