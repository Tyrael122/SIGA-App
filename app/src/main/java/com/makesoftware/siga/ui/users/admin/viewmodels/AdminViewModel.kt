package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.DataGridView
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource
import com.makesoftware.siga.data.repositories.CoursesRepository
import com.makesoftware.siga.data.repositories.TeachersRepository
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

    private val remoteDataSource = RemoteDataSource(
        api = SIGAJavaApi()
    )

    private val courseRepository: CoursesRepository = CoursesRepository(
        remoteDataSource
    )

    private val teacherRepository = TeachersRepository(
        remoteDataSource
    )

    private var _courseUiState = MutableStateFlow(AdminCourseUiState())
    val courseUiState = _courseUiState.asStateFlow()

    // TODO: Do the teacher UI state stuff, and create the teacher registration screen. Then allow for teacher creation.
    private var _teacherUiState = MutableStateFlow(AdminTeacherUiState())
    val teacherUiState = _teacherUiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchCourses(context: Context) {
        doFetchJob(updateDataGridState = { dataGridState ->
            _courseUiState.update {
                it.copy(dataGridState = dataGridState)
            }
        }, updateData = {
            val courses = courseRepository.fetchCourses()
            updateDataGridView(_courseUiState, courses) { uiState, dataGridState ->
                uiState.copy(
                    courses = courses, dataGridState = dataGridState
                )
            }
        }, context = context)
    }

    // Leave at that, for now. If it ever happens that this function becomes way too complex, then we can use reflection?
    fun fetchTeachers(context: Context) {
        doFetchJob(updateDataGridState = { dataGridState ->
            _teacherUiState.update {
                it.copy(dataGridState = dataGridState)
            }
        }, updateData = {
            val teachers = teacherRepository.fetchTeachers()
            updateDataGridView(_teacherUiState, teachers) { uiState, dataGridState ->
                uiState.copy(
                    teachers = teachers, dataGridState = dataGridState
                )
            }
        }, context = context)
    }

    private fun doFetchJob(
        updateDataGridState: (DataGridState) -> Unit,
        updateData: suspend () -> Unit,
        context: Context
    ) {
        fetchJob?.cancel("Restarting fetch job")

        if (!arePrerequisitesMet(updateDataGridState, context)) {
            return
        }

        updateDataGridState(DataGridState.Loading)

        fetchJob = viewModelScope.launch {
            try {
                updateData()

            } catch (ioe: IOException) {
                updateDataGridState(
                    DataGridState.Error(
                        ErrorType.UNKNOWN, "Oops. Algo deu errado. Tente novamente."
                    )
                )
            }
        }
    }

    private fun arePrerequisitesMet(
        updateDataGridState: (DataGridState) -> Unit, context: Context
    ): Boolean {
        if (!hasNetworkConnection(context)) {
            // TODO: Make an interface or superclass for update data grid state.
            updateDataGridState(
                DataGridState.Error(
                    ErrorType.NO_NETWORK, "Você não está conectado à internet."
                )
            )
            return false
        }

        return true
    }
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

private fun <T> updateDataGridView(
    mutableStateFlow: MutableStateFlow<T>,
    items: List<DataGridView>,
    update: (T, DataGridState) -> T
) {
    mutableStateFlow.update {
        update(
            it, DataGridState.Success(items.map { item ->
                item.toDataGridView()
            })
        )
    }
}

data class AdminTeacherUiState(
    val selectedTeacher: Teacher? = null,
    val teachers: List<Teacher> = listOf(),
    val dataGridState: DataGridState = DataGridState.Success(),
)

data class AdminCourseUiState(
    val selectedCourse: Course? = null,
    val courses: List<Course> = listOf(),
    val dataGridState: DataGridState = DataGridState.Success(),
)

sealed class DataGridState {
    data class Success(val items: List<DataGridRowContent> = listOf()) : DataGridState()
    object Loading : DataGridState()
    data class Error(val errorType: ErrorType, val message: String) : DataGridState()
}

enum class ErrorType {
    NO_NETWORK, UNKNOWN
}
