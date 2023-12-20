package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource
import com.makesoftware.siga.data.repositories.CoursesRepository
import com.makesoftware.siga.data.repositories.TeachersRepository
import com.makesoftware.siga.network.FetchJobManager
import com.makesoftware.siga.network.FetchResult
import com.makesoftware.siga.network.SIGAJavaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CourseViewModel : ViewModel() {

    private val remoteDataSource = RemoteDataSource()

    private val courseRepository: CoursesRepository = CoursesRepository(
        remoteDataSource
    )

    private var _courseUiState = MutableStateFlow(AdminCourseUiState())
    val courseUiState = _courseUiState.asStateFlow()

    private val fetchJobManager = FetchJobManager()


    fun fetchCourses(context: Context) {
        viewModelScope.launch {
            fetchJobManager.doFetchJob(updateFetchResult = { fetchResult ->
                _courseUiState.update {
                    it.copy(fetchResult = fetchResult)
                }
            }, fetchData = {
                courseRepository.fetchCourses()
            }, context = context)
        }
    }

    fun selectCourse(course: Course) {
        _courseUiState.update {
            it.copy(selectedCourse = course.copy(isUpdate = true))
        }
    }

    fun updateSelectedCourse(course: Course) {
        _courseUiState.update {
            it.copy(selectedCourse = course)
        }
    }

    fun clearCourseForm() {
        updateSelectedCourse(
            Course(
                name = "",
                acronym = "",
            )
        )
    }

    fun saveCourseUpdate() {
        // TODO: Implement this.
    }

    fun saveCourse() {
        // TODO: Implement this.
    }
}

data class AdminCourseUiState(
    val selectedCourse: Course = Course(
        name = "",
        acronym = "",
    ),
    val fetchResult: FetchResult<Course> = FetchResult.Success(),
)
