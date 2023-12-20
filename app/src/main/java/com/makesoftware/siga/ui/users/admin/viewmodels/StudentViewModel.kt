package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.datasources.RemoteDataSource
import com.makesoftware.siga.data.repositories.StudentsRepository
import com.makesoftware.siga.network.FetchJobManager
import com.makesoftware.siga.network.FetchResult
import com.makesoftware.siga.network.SIGAJavaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StudentViewModel : ViewModel() {

    private val remoteDataSource = RemoteDataSource(
        api = SIGAJavaApi()
    )

    private val courseRepository: StudentsRepository = StudentsRepository(
        remoteDataSource
    )

    private var _studentUiState = MutableStateFlow(AdminStudentUiState())
    val studentUiState = _studentUiState.asStateFlow()

    private val fetchJobManager = FetchJobManager()

    fun fetchStudent(context: Context) {
        viewModelScope.launch {
            fetchJobManager.doFetchJob(updateFetchResult = { fetchResult ->
                _studentUiState.update {
                    it.copy(fetchResult = fetchResult)
                }
            }, fetchData = {
                courseRepository.fetchStudent()
            }, context = context)
        }
    }

    fun selectStudent(student: Student) {
        _studentUiState.update {
            it.copy(selectedStudent = student.copy(isUpdate = true))
        }
    }

    fun updateSelectedStudent(student: Student) {
        _studentUiState.update {
            it.copy(selectedStudent = student)
        }
    }

    fun clearStudentForm() {
        updateSelectedStudent(
            Student()
        )
    }

    fun saveStudentUpdate() {
        // TODO: Implement this.
    }

    fun saveStudent() {
        // TODO: Implement this.
    }
}

data class AdminStudentUiState(
    val selectedStudent: Student = Student(),
    val fetchResult: FetchResult<Student> = FetchResult.Success(),
)
