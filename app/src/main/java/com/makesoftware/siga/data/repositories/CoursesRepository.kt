package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.datasources.RemoteDataSource

class CoursesRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun fetchCourses(): List<Course> {
        return remoteDataSource.fetchCourses()
    }
}