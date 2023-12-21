package com.makesoftware.siga.data.repositories

interface BasicCrudRepository<T> {
    suspend fun fetchAll(): List<T>

    //    suspend fun fetchById(id: Long)
    suspend fun save(entity: T)
}