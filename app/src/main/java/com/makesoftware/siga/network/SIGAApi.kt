package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import com.makesoftware.siga.data.datasources.EndpointPrefixes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SIGAApi {
    @GET("courses")
    suspend fun fetchCourses(): List<Course>

    @GET("teachers")
    suspend fun fetchTeachers(): List<Teacher>

    suspend fun fetchStudent(): List<Student>

    @POST("${EndpointPrefixes.USER}/{id}/teachers")
    suspend fun postTeacher(@Path("id") userId: Long, @Body teacher: Teacher)

    @POST(EndpointPrefixes.USER)
    suspend fun postUser(@Body user: User): User
}
