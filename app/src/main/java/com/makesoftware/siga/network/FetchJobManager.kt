package com.makesoftware.siga.network

import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FetchJobManager(private val networkManager: NetworkManager) {

    private var fetchJob: Job? = null

    suspend fun <T> doFetchJob(
        updateFetchResult: (FetchResult<T>) -> Unit,
        fetchData: suspend () -> List<T>
    ) { // TODO: Make this function return a fetchResult.
        fetchJob?.cancel("Restarting fetch job")

        if (!arePrerequisitesMet(updateFetchResult)) {
            return
        }

        updateFetchResult(FetchResult.Loading())

        fetchJob = coroutineScope {
            launch {
                try {
                    updateFetchResult(
                        FetchResult.Success(
                            items = fetchData()
                        )
                    )

                } catch (ex: Exception) {
                    Log.e("FetchJobManager", "Exception: ${ex.message}")
                    Log.e("FetchJobManager", "Exception cause: ${ex.cause?.message}")
                    updateFetchResult(
                        FetchResult.Error(
                            ErrorType.UNKNOWN, "Oops. Algo deu errado. Tente novamente."
                        )
                    )
                }
            }
        }
    }

    private fun <K> arePrerequisitesMet(
        updateFetchResult: (FetchResult<K>) -> Unit
    ): Boolean {
        if (!networkManager.hasNetworkConnection()) {
            Log.d("FetchJobManager", "No network connection.")
            updateFetchResult(
                FetchResult.Error(
                    ErrorType.NO_NETWORK, "Você não está conectado à internet."
                )
            )
            return false
        }

        return true
    }
}

// Out makes this accept subtypes of T,
// so if I declare I want a FetchResult<DataGridView>, I can pass a FetchResult<Course> to it.
sealed class FetchResult<out T> {
    data class Success<T>(val items: List<T> = listOf()) : FetchResult<T>()
    data class Loading<T>(val message: String = "") : FetchResult<T>()
    data class Error<T>(val errorType: ErrorType, val message: String) : FetchResult<T>()

    fun getSucessItemsOrEmptyList(): List<T> {
        return when (this) {
            is Success -> this.items
            else -> emptyList()
        }
    }
}

enum class ErrorType {
    NO_NETWORK, UNKNOWN
}