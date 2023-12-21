package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Subject
import com.makesoftware.siga.data.datasources.RemoteDataSource

class SubjectRepository(
    private val remoteDataSource: RemoteDataSource
) : BasicCrudRepository<Subject> {

    override suspend fun fetchAll(): List<Subject> {
        return remoteDataSource.fetchSubjects()
    }

    override suspend fun save(entity: Subject) {
        remoteDataSource.postSubject(entity)
    }
}