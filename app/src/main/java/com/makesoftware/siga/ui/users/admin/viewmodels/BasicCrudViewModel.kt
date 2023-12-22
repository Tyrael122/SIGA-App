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

    private var _uiState = MutableStateFlow(UiState(selectedEntity = entityFactory()))
    val uiState = _uiState.asStateFlow()

    private var _selectableUiState = MutableStateFlow(SelectableUiState<T>())
    val selectableUiState = _selectableUiState.asStateFlow()

    private val fetchJobManager = FetchJobManager()

    // TODO: Make a NetworkManager class to handle network stuff, and use DI to inject it into the view model.
    //  This will allow us to call the fetchAllEntities method without passing context. Don't pass context to viewmodel.
//    init {
//        fetchAllEntities(context)
//    }

    fun selectEntityForUpdate(entity: T) {
        _uiState.update {
            it.copy(selectedEntity = entity, isEntityBeingUpdated = true)
        }
    }

    fun fetchAllEntities(context: Context) {
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

    fun setOnCommitSelection(onCommitSelection: (List<T>) -> Unit) {
        _selectableUiState.update {
            it.copy(onCommitSelection = onCommitSelection)
        }
    }

    fun clearSelectableState() {
        _selectableUiState.update {
            it.copy(selectedEntities = emptyList(), onCommitSelection = {}, isViewSelectable = false)
        }
    }

    fun updateSelectedEntities(entities: List<T>) {
        _selectableUiState.update {
            it.copy(selectedEntities = entities)
        }
    }

    fun clearFormState() {
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

                // TODO: Refactor these toasts out of the viewmodel.
                Toast.makeText(context, "Erro ao salvar.", Toast.LENGTH_SHORT).show()

                return@launch
            }

            // TODO: Refactor these toasts out of the viewmodel.
            Toast.makeText(context, "Salvo com sucesso.", Toast.LENGTH_SHORT).show()

            clearFormState()
        }
    }

    fun getFetchResultSucessItemsOrEmptyList(): List<T> {
        return when (val fetchResult = _uiState.value.fetchResult) {
            is FetchResult.Success -> fetchResult.items
            else -> emptyList()
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

data class SelectableUiState<T>(
    val isViewSelectable: Boolean = false,
    val selectedEntities: List<T> = emptyList(),
    val onCommitSelection: (List<T>) -> Unit = {},
)