package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.datasources.RemoteDataSource

class TeachersRepository(
    private val remoteDataSource: RemoteDataSource
): BasicCrudRepository<Teacher>  {

    override suspend fun fetchAll(): List<Teacher> {
        return remoteDataSource.fetchTeachers()
    }

    override suspend fun save(entity: Teacher) {
        val postedUser = remoteDataSource.postUser(entity.user)

        remoteDataSource.postTeacher(postedUser.id, entity)
    }
}