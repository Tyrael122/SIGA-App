package com.makesoftware.siga.ui.commons.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.makesoftware.siga.network.FetchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectionViewModel<T>(
    fetchResult: FetchResult<T>
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(SelectableUiState(fetchedItems = fetchResult.getSucessItemsOrEmptyList()))
    val uiState = _uiState.asStateFlow()

    private val fetchedItems: List<T> = fetchResult.getSucessItemsOrEmptyList()

    private val selectedItems: List<T>
        get() {
            return _uiState.value.selectedItems
        }

    fun toggleItemSelection(item: T) {
        updateSelectionUiState(
            if (selectedItems.contains(item)) {
                selectedItems.minus(item)
            } else {
                selectedItems.plus(item)
            }
        )
    }

    fun selectAllItems() {
        updateSelectionUiState(
            if (isAllItemsSelected()) {
                emptyList()
            } else {
                fetchedItems
            }
        )
    }

    private fun updateSelectionUiState(newSelectedItems: List<T>) {
        _uiState.update {
            it.copy(selectedItems = newSelectedItems)
        }

        _uiState.update {
            it.copy(isAllItemsSelected = isAllItemsSelected())
        }
    }

    private fun isAllItemsSelected(): Boolean {
        return selectedItems.size == fetchedItems.size
    }

    companion object {
        fun <T> Factory(
            fetchResult: FetchResult<T>
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    SelectionViewModel(
                        fetchResult
                    )
                }
            }
        }
    }
}

data class SelectableUiState<T>(
    val selectedItems: List<T> = emptyList(),
    val isAllItemsSelected: Boolean = false,
    val fetchedItems: List<T>
)
