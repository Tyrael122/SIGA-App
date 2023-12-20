package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.datasources.RemoteDataSource

class StudentsRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun fetchStudent(): List<Student> {
        return remoteDataSource.fetchStudent()
    }
}