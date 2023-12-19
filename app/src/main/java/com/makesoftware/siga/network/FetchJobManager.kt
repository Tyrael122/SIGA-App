package com.makesoftware.siga.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

class FetchJobManager {

    private var fetchJob: Job? = null

    suspend fun <T> doFetchJob(
        updateFetchResult: (FetchResult<T>) -> Unit,
        fetchData: suspend () -> List<T>,
        context: Context
    ) {
        fetchJob?.cancel("Restarting fetch job")


        if (!arePrerequisitesMet(updateFetchResult, context)) {
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

                } catch (ioe: IOException) {
                    updateFetchResult(
                        FetchResult.Error(
                            ErrorType.UNKNOWN, "Oops. Algo deu errado. Tente novamente."
                        )
                    )
                }
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
}

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