package com.makesoftware.siga.data.datasources

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.network.SIGAApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val api: SIGAApi, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchCourses(): List<Course> {
        return withContext(ioDispatcher) {
            api.fetchCourses()
        }
    }

    suspend fun fetchTeachers(): List<Teacher> {
        return withContext(ioDispatcher) {
            api.fetchTeachers()
        }
    }

    suspend fun fetchStudent(): List<Student> {
        return withContext(ioDispatcher) {
            api.fetchStudent()
        }
    }
}