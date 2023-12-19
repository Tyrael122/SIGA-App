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
        doFetchJob(updateFetchResult = { fetchResult ->
            _courseUiState.update {
                it.copy(fetchResult = fetchResult)
            }
        }, fetchData = {
            courseRepository.fetchCourses()
        }, context = context)
    }

    fun fetchTeachers(context: Context) {
        doFetchJob(updateFetchResult = { fetchResult ->
            _teacherUiState.update {
                it.copy(fetchResult = fetchResult)
            }
        }, fetchData = {
            teacherRepository.fetchTeachers()
        }, context = context)
    }

    private fun <T> doFetchJob(
        updateFetchResult: (FetchResult<T>) -> Unit,
        fetchData: suspend () -> List<T>,
        context: Context
    ) {
        fetchJob?.cancel("Restarting fetch job")

        if (!arePrerequisitesMet(updateFetchResult, context)) {
            return
        }

        updateFetchResult(FetchResult.Loading())

        fetchJob = viewModelScope.launch {
            try {
                updateFetchResult(
                    FetchResult.Success(
                        items = fetchData()
                    )
                )

            } catch (ioe: IOException) {
                updateFetchResult(
                    FetchResult.Error(
                        ErrorType.UNKNOWN, "Oops. Algo deu errado. Tente novamente."
                    )
                )
            }
        }
    }

    private fun <T> arePrerequisitesMet(
        updateDataGridState: (FetchResult<T>) -> Unit, context: Context
    ): Boolean {
        if (!hasNetworkConnection(context)) {
            updateDataGridState(
                FetchResult.Error(
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

data class AdminTeacherUiState(
    val selectedTeacher: Teacher? = null,
    val fetchResult: FetchResult<Teacher> = FetchResult.Success(),
)

data class AdminCourseUiState(
    val selectedCourse: Course? = null,
    val fetchResult: FetchResult<Course> = FetchResult.Success(),
)

// Out makes this accept subtypes of T,
// so if I declare I want a FetchResult<DataGridView>, I can pass a FetchResult<Course> to it.
sealed class FetchResult<out T> {
    data class Success<T>(val items: List<T> = listOf()) : FetchResult<T>()
    data class Loading<T>(val message: String = "") : FetchResult<T>()
    data class Error<T>(val errorType: ErrorType, val message: String) : FetchResult<T>()
}

enum class ErrorType {
    NO_NETWORK, UNKNOWN
}
