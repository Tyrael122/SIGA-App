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
import com.makesoftware.siga.network.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BasicCrudViewModel<T>(
    private val repository: BasicCrudRepository<T>,
    private val entityFactory: () -> T,
    networkManager: NetworkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(selectedEntity = entityFactory()))
    val uiState = _uiState.asStateFlow()

    private var _selectableUiState = MutableStateFlow(SelectableUiState<T>())
    val selectableUiState = _selectableUiState.asStateFlow()

    private val fetchJobManager = FetchJobManager(networkManager)

    init {
        fetchAllEntities()
    }

    fun selectEntityForUpdate(entity: T) {
        _uiState.update {
            it.copy(selectedEntity = entity, isEntityBeingUpdated = true)
        }
    }

    fun fetchAllEntities() {
        viewModelScope.launch {
            fetchJobManager.doFetchJob(updateFetchResult = { fetchResult ->
                _uiState.update {
                    it.copy(fetchResult = fetchResult)
                }
            }) {
                repository.fetchAll()
            }
        }
    }

    fun updateSelectedEntity(entity: T) {
        _uiState.update {
            it.copy(selectedEntity = entity)
        }
    }

    fun setViewAsSelectableWithCallbackBuilder(
        initialSelectedEntities: List<T>,
        navigateToPreviousScreen: () -> Unit,
        navigateToSelectionScreen: () -> Unit,
        updateEntitySelection: (List<T>) -> Unit
    ) {
        _selectableUiState.update {
            it.copy(
                selectedEntities = initialSelectedEntities,
            )
        }

        _selectableUiState.update {
            it.copy(onCommitSelection = { selectedEntities ->
                navigateToPreviousScreen()
                updateEntitySelection(selectedEntities)

            }, isViewSelectable = true)
        }

        navigateToSelectionScreen()
    }

    fun setViewAsSelectableWithCallback(
        initialSelectedEntities: List<T>, onCommitSelection: (List<T>) -> Unit
    ) {
        _selectableUiState.update {
            it.copy(
                selectedEntities = initialSelectedEntities,
            )
        }

        _selectableUiState.update {
            it.copy(onCommitSelection = onCommitSelection, isViewSelectable = true)
        }
    }

    fun clearSelectableState() {
        _selectableUiState.update {
            it.copy(
                selectedEntities = emptyList(), onCommitSelection = {}, isViewSelectable = false
            )
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

    companion object {
        fun <T> Factory(
            repository: BasicCrudRepository<T>,
            networkManager: NetworkManager,
            entityFactory: () -> T,
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    BasicCrudViewModel(
                        repository, entityFactory, networkManager
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