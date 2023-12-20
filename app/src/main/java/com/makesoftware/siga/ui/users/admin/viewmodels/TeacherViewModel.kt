package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource
import com.makesoftware.siga.data.repositories.TeachersRepository
import com.makesoftware.siga.network.FetchJobManager
import com.makesoftware.siga.network.FetchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TeacherViewModel : ViewModel() {

    private val remoteDataSource = RemoteDataSource()

    private val teacherRepository = TeachersRepository(
        remoteDataSource
    )

    // TODO: Do the teacher UI state stuff, and create the teacher registration screen. Then allow for teacher creation.
    private var _teacherUiState = MutableStateFlow(AdminTeacherUiState())
    val teacherUiState = _teacherUiState.asStateFlow()

    private val fetchJobManager = FetchJobManager()


    fun selectTeacher(teacher: Teacher) {
        _teacherUiState.update {
            it.copy(selectedTeacher = teacher.copy(isUpdate = true))
        }
    }

    fun fetchTeachers(context: Context) {
        viewModelScope.launch {
            fetchJobManager.doFetchJob(updateFetchResult = { fetchResult ->
                _teacherUiState.update {
                    it.copy(fetchResult = fetchResult)
                }
            }, fetchData = {
                teacherRepository.fetchTeachers()
            }, context = context)
        }
    }

    fun updateSelectedTeacher(teacher: Teacher?) {
        _teacherUiState.update {
            it.copy(selectedTeacher = teacher)
        }
    }

    fun clearSelectedTeacher() {
        updateSelectedTeacher(
            null
        )
    }

    fun saveTeacherUpdate() {
        // TODO: Implement this.
    }

    fun saveTeacher() {
        viewModelScope.launch {
//            teacherRepository.saveTeacher(teacherUiState.value.selectedTeacher)
        }

        clearSelectedTeacher()
    }
}

data class AdminTeacherUiState(
    val selectedTeacher: Teacher? = null,
    val fetchResult: FetchResult<Teacher> = FetchResult.Success(),
)