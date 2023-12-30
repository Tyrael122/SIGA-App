package com.makesoftware.siga.data.repositories

import com.makesoftware.siga.data.Course
import com.makesoftware.siga.data.CourseDTO
import com.makesoftware.siga.data.datasources.RemoteDataSource

class CoursesRepository(
    private val remoteDataSource: RemoteDataSource
) : BasicCrudRepository<Course> {

    override suspend fun fetchAll(): List<Course> {
        return remoteDataSource.fetchCourses()
    }

    override suspend fun save(entity: Course) {
        val courseDto = convertCourseToDto(entity)

        remoteDataSource.postCourse(courseDto)
    }

    private fun convertCourseToDto(entity: Course) = CourseDTO(
        id = entity.id,
        name = entity.name,
        acronym = entity.acronym,
        description = entity.description,
        numberOfSemesters = entity.numberOfSemesters,
        maxNumbersOfSemestersToFinish = entity.maxNumbersOfSemestersToFinish,
        subjectIds = entity.subjects.map { it.id }
    )
}