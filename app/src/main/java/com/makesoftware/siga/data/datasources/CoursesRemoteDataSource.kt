package com.makesoftware.siga.data.datasources

import com.makesoftware.siga.network.SIGAApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesRemoteDataSource(
    private val api: SIGAApi, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchCourses(): List<Course> {
        return withContext(ioDispatcher) {
            api.fetchCourses()
        }
    }
}


data class Course(
    val name: String,
    val acronym: String,
)