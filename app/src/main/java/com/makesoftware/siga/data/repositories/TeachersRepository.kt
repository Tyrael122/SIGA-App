package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource

class TeachersRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun fetchTeachers(): List<Teacher> {
        return remoteDataSource.fetchTeachers()
    }
}