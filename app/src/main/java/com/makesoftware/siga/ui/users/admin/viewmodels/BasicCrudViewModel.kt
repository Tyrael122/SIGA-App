package com.makesoftware.siga.ui.users.admin.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.makesoftware.siga.data.repositories.BasicCrudRepository
import com.makesoftware.siga.network.FetchJobManager
import com.makesoftware.siga.network.FetchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BasicCrudViewModel<T>(
    private val repository: BasicCrudRepository<T>, private val entityFactory: () -> T
) : ViewModel() {


    private var _uiState = MutableStateFlow(
        UiState(
            selectedEntity = entityFactory()
        )
    )
    val uiState = _uiState.asStateFlow()

    private val fetchJobManager = FetchJobManager()


    fun selectEntity(entity: T) {
        _uiState.update {
            it.copy(selectedEntity = entity, isEntityBeingUpdated = true)
        }
    }

    fun fetchEntity(context: Context) {
        viewModelScope.launch {
            fetchJobManager.doFetchJob(updateFetchResult = { fetchResult ->
                _uiState.update {
                    it.copy(fetchResult = fetchResult)
                }
            }, fetchData = {
                repository.fetchAll()
            }, context = context)
        }
    }

    fun updateSelectedEntity(entity: T) {
        _uiState.update {
            it.copy(selectedEntity = entity)
        }
    }

    fun clearForm() {
        _uiState.update {
            it.copy(selectedEntity = entityFactory(), isEntityBeingUpdated = false)
        }
    }

    fun updateEntity() {
        // TODO: Implement this.
    }

    fun saveEntity(context: Context) {
        viewModelScope.launch {
            try {
                repository.save(_uiState.value.selectedEntity)
            } catch (e: Exception) {
                Log.e("TeacherViewModel", "Error: ${e.message}")
                Toast.makeText(context, "Erro ao salvar.", Toast.LENGTH_SHORT).show()

                return@launch
            }

            Toast.makeText(context, "Salvo com sucesso.", Toast.LENGTH_SHORT).show()

            clearForm()
        }
    }

    companion object {
        fun <T> Factory(
            repository: BasicCrudRepository<T>, entityFactory: () -> T
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    BasicCrudViewModel(
                        repository, entityFactory
                    )
                }
            }
        }
    }
}

data class UiState<T>(
    val selectedEntity: T,
    val isEntityBeingUpdated: Boolean = false,
    val fetchResult: FetchResult<T> = FetchResult.Success(),
)