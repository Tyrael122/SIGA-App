package com.makesoftware.siga.ui.users.admin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.datasources.Course
import com.makesoftware.siga.data.datasources.CoursesRemoteDataSource
import com.makesoftware.siga.data.repositories.CoursesRepository
import com.makesoftware.siga.network.SIGAJavaApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class AdminViewModel : ViewModel() {

    private val repository: CoursesRepository = CoursesRepository(
        CoursesRemoteDataSource(
            api = SIGAJavaApi()
        )
    )

    private var _courseUiState = MutableStateFlow(AdminCourseUiState())
    val courseUiState = _courseUiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchCourses() {
        fetchJob?.cancel("Restarting fetch job")

        _courseUiState.update {
            it.copy(isLoading = true)
        }

        fetchJob = viewModelScope.launch {
            try {
                val courses = repository.fetchCourses()
                _courseUiState.update {
                    it.copy(courses = courses, isLoading = false)
                }

            } catch (ioe: IOException) {
                // TODO: Handle the error and notify the UI when appropriate.
            }
        }
    }
}

data class AdminCourseUiState(
    val courses: List<Course> = listOf(),
    val isLoading: Boolean = false // TODO: Change these variable for a sealed class for better error handling with messages and stuff
)