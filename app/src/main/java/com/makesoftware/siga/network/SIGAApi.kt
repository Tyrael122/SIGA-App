package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Teacher
import retrofit2.http.GET

interface SIGAApi {
    @GET("courses")
    suspend fun fetchCourses(): List<Course>

    @GET("teachers")
    suspend fun fetchTeachers(): List<Teacher>

    suspend fun fetchStudent(): List<Student>
    suspend fun postTeacher(teacher: Teacher)
}
