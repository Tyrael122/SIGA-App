package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Student
import com.makesoftware.siga.data.StudentDTO
import com.makesoftware.siga.data.datasources.RemoteDataSource

class StudentsRepository(
    private val remoteDataSource: RemoteDataSource
) : BasicCrudRepository<Student> {

    override suspend fun fetchAll(): List<Student> {
        return remoteDataSource.fetchStudent()
    }

    override suspend fun save(entity: Student) {
        val postedUser = remoteDataSource.postUser(entity.user)

        val studentDto = convertStudentToDto(entity)

        remoteDataSource.postStudent(postedUser.id, studentDto)
    }

    private fun convertStudentToDto(entity: Student): StudentDTO {
        return StudentDTO(
            entity.enrolledCourse.id,
            entity.courseSemester,
            entity.enrolledSubjects.map { it.id },
            entity.user
        )
    }
}