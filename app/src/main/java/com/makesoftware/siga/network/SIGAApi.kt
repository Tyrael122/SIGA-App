package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.Teacher

interface SIGAApi {
    suspend fun fetchCourses(): List<Course>
    suspend fun fetchTeachers(): List<Teacher>
    suspend fun fetchStudent(): List<Student>
}
