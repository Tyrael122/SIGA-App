package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.datasources.Course
import com.makesoftware.siga.data.datasources.CoursesRemoteDataSource
import com.makesoftware.siga.data.repositories.CoursesRepository
import com.makesoftware.siga.network.SIGAJavaApi
import com.makesoftware.siga.ui.commons.components.DataGridRowContent
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

    fun fetchCourses(context: Context) {
        fetchJob?.cancel("Restarting fetch job")

        if (!arePrerequisitesMet(context)) {
            return
        }

        _courseUiState.update {
            it.copy(dataGridView = DataGridState.Loading)
        }

        fetchJob = viewModelScope.launch {
            try {
                val courses = repository.fetchCourses()
                updateCourses(courses)

            } catch (ioe: IOException) {
                _courseUiState.update {
                    it.copy(
                        dataGridView = DataGridState.Error(
                            ErrorType.UNKNOWN, "Oops. Algo deu errado. Tente novamente."
                        )
                    )
                }
            }
        }
    }

    private fun arePrerequisitesMet(context: Context): Boolean {
        if (!hasNetworkConnection(context)) {
            _courseUiState.update {
                it.copy(
                    dataGridView = DataGridState.Error(
                        ErrorType.NO_NETWORK, "Você não está conectado à internet."
                    )
                )
            }
            return false
        }

        return true
    }

    private fun hasNetworkConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false

        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        ) {
            return true
        }

        return false
    }

    private fun updateCourses(courses: List<Course>) {
        _courseUiState.update {
            it.copy(
                courses = courses, dataGridView = DataGridState.Success(courses.map { course ->
                    DataGridRowContent(
                        listOf(
                            course.name, course.acronym
                        )
                    )
                })
            )
        }
    }
}

data class AdminCourseUiState(
    val courses: List<Course> = listOf(),
    val dataGridView: DataGridState = DataGridState.Success(listOf()),
    val selectedCourse: Course? = null,
)

sealed class DataGridState {
    data class Success(val items: List<DataGridRowContent>) : DataGridState()
    object Loading : DataGridState()
    data class Error(val errorType: ErrorType, val message: String) : DataGridState()
}

enum class ErrorType {
    NO_NETWORK, UNKNOWN
}
