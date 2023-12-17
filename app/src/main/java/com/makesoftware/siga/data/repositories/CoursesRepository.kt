package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.datasources.Course
import com.makesoftware.siga.data.datasources.CoursesRemoteDataSource

class CoursesRepository(
    private val coursesRemoteDataSource: CoursesRemoteDataSource
) {

    suspend fun fetchCourses(): List<Course> {
        return coursesRemoteDataSource.fetchCourses()
    }
}