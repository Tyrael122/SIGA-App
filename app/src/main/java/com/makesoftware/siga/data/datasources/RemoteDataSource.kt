package com.makesoftware.siga.data.datasources

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.StudentDTO
import com.makesoftware.siga.data.Subject
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import com.makesoftware.siga.network.SIGAApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val BASE_URL = "http://192.168.18.62:8080/"


private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).build()


class RemoteDataSource(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val retrofitService: SIGAApi by lazy {
        retrofit.create(SIGAApi::class.java)
    }

    suspend fun fetchCourses(): List<Course> {
        return withContext(ioDispatcher) {
            retrofitService.fetchCourses()
        }
    }

    suspend fun fetchTeachers(): List<Teacher> {
        return withContext(ioDispatcher) {
            retrofitService.fetchTeachers()
        }
    }

    suspend fun fetchStudent(): List<Student> {
        return withContext(ioDispatcher) {
            retrofitService.fetchStudent()
        }
    }

    suspend fun fetchSubjects(): List<Subject> {
        return withContext(ioDispatcher) {
            retrofitService.fetchSubjects()
        }
    }

    suspend fun postTeacher(userId: Long, teacher: Teacher) {
        withContext(ioDispatcher) {
            retrofitService.postTeacher(userId, teacher)
        }
    }

    suspend fun postUser(user: User): User {
        return withContext(ioDispatcher) {
            retrofitService.postUser(user)
        }
    }

    suspend fun postStudent(userId: Long, student: StudentDTO) {
        withContext(ioDispatcher) {
            retrofitService.postStudent(userId, student)
        }
    }

    suspend fun postSubject(subject: Subject) {
        withContext(ioDispatcher) {
            retrofitService.postSubject(subject)
        }
    }

    suspend fun postCourse(entity: Course) {
        withContext(ioDispatcher) {
            retrofitService.postCourse(entity)
        }
    }
}