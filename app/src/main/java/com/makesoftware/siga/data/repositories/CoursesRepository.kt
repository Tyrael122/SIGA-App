package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.datasources.RemoteDataSource

class CoursesRepository(
    private val remoteDataSource: RemoteDataSource
) : BasicCrudRepository<Course> {

    override suspend fun fetchAll(): List<Course> {
        return remoteDataSource.fetchCourses()
    }

    override suspend fun save(entity: Course) {
        TODO("Not yet implemented")
    }
}