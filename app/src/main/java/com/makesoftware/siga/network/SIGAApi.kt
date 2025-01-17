package com.makesoftware.siga.network

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.CourseDTO
import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.StudentDTO
import com.makesoftware.siga.data.Subject
import com.makesoftware.siga.data.Teacher
import com.makesoftware.siga.data.User
import com.makesoftware.siga.data.datasources.EndpointPrefixes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SIGAApi {
    @GET(EndpointPrefixes.COURSE)
    suspend fun fetchCourses(): List<Course>

    @GET(EndpointPrefixes.TEACHER)
    suspend fun fetchTeachers(): List<Teacher>

    @GET(EndpointPrefixes.STUDENT)
    suspend fun fetchStudent(): List<Student>

    @POST("${EndpointPrefixes.USER}/{id}/${EndpointPrefixes.TEACHER}")
    suspend fun postTeacher(@Path("id") userId: Long, @Body teacher: Teacher)

    @POST(EndpointPrefixes.USER)
    suspend fun postUser(@Body user: User): User

    @POST("${EndpointPrefixes.USER}/{id}/${EndpointPrefixes.STUDENT}")
    suspend fun postStudent(@Path("id") userId: Long, @Body student: StudentDTO)

    @GET(EndpointPrefixes.SUBJECT)
    suspend fun fetchSubjects(): List<Subject>

    @POST(EndpointPrefixes.SUBJECT)
    suspend fun postSubject(@Body subject: Subject)

    @POST(EndpointPrefixes.COURSE)
    suspend fun postCourse(@Body entity: CourseDTO)
}
