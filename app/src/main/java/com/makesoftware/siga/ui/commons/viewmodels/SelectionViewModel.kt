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
    previouslySelectedItems: List<T>,
    fetchedItems: List<T>
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectableUiState(fetchedItems = fetchedItems))
    val uiState = _uiState.asStateFlow()

    private val fetchedItems: List<T>
        get() {
            return _uiState.value.fetchedItems
        }

//    private var fetchResult: FetchResult<T>? = null

    private val selectedItems: List<T>
        get() {
            return _uiState.value.selectedItems
        }

    init {
        updateSelectionUiState(previouslySelectedItems)
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
//        if (fetchResult == null) {
//            throw Exception("Fetch result is null. Did you forget to set the fetched items?")
//        }

        if (fetchedItems.isEmpty()) {
            return false
        }

        return selectedItems.size == fetchedItems.size
    }

    fun setFetchedItemsByFetchResult(fetchResult: FetchResult<T>) {
//        this.fetchResult = fetchResult

        val newFetchedItems = fetchResult.getSucessItemsOrEmptyList()

        _uiState.update {
            it.copy(fetchedItems = newFetchedItems)
        }
    }

    companion object {
        fun <T> Factory(
            selectedItems: List<T>,
            fetchedItems: List<T>
        ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    SelectionViewModel(selectedItems, fetchedItems)
                }
            }
        }
    }
}

data class SelectableUiState<T>(
    val selectedItems: List<T> = emptyList(),
    val isAllItemsSelected: Boolean = false,
    val fetchedItems: List<T> = emptyList(),
)
